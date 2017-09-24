package pega;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import pega.command.executor.CommandBus;
import pega.command.executor.CommandBus.ExecutorNotFoundException;
import pega.command.executor.MergeCommandExecutor;
import pega.command.executor.SortCommandExecutor;
import pega.util.TmpFileProvider;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MergeSortFileTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private File inputFile;
    private File outputFile;
    private final CommandBus commandBus = new CommandBus(Arrays.asList(
        new SortCommandExecutor(),
        new MergeCommandExecutor()
    ));
    private TmpFileProvider tmpFileProvider;

    @Before
    public void setUp() throws IOException {
        inputFile = folder.newFile("input");
        outputFile = folder.newFile("output");

        tmpFileProvider = mock(TmpFileProvider.class);
    }

    @Test
    public void testFileFittingIntoMemory() throws IOException, ExecutorNotFoundException {
        int inputFileSize = 100;
        fillInputFile(inputFileSize);

        MergeSortFile mergeSortFile = new MergeSortFile(
            commandBus,
            inputFile,
            outputFile,
            inputFileSize,
            100,
            tmpFileProvider,
            1
        );

        mergeSortFile.sort();

        assertArrayEquals(
            IntStream.rangeClosed(1, 100).toArray(),
            readOutputFile()
        );
    }

    @Test
    public void testFileTwiceAsLargeAsMemory() throws IOException, ExecutorNotFoundException {
        int inputFileSize = 100;
        fillInputFile(inputFileSize);
        int maxMemory = 50;

        createTmpFiles();

        MergeSortFile mergeSortFile = new MergeSortFile(
            commandBus,
            inputFile,
            outputFile,
            inputFileSize,
            maxMemory,
            tmpFileProvider,
            1
        );

        mergeSortFile.sort();

        assertArrayEquals(
            IntStream.rangeClosed(1, 100).toArray(),
            readOutputFile()
        );

        verify(tmpFileProvider, times(2)).create();
    }

    @Test
    public void testFileThreeTimesAsLargeAsMemory() throws IOException, ExecutorNotFoundException {
        int inputFileSize = 150;
        int maxMemory = 50;
        fillInputFile(inputFileSize);

        createTmpFiles();

        MergeSortFile mergeSortFile = new MergeSortFile(
            commandBus,
            inputFile,
            outputFile,
            inputFileSize,
            maxMemory,
            tmpFileProvider,
            1
        );

        mergeSortFile.sort();

        assertArrayEquals(
            IntStream.rangeClosed(1, 150).toArray(),
            readOutputFile()
        );

        verify(tmpFileProvider, times(4)).create();
    }

    private void fillInputFile(int size) throws IOException {
        try (RandomAccessFile outputFile = new RandomAccessFile(
            inputFile,
            "rw"
        )) {
            for (int i = size; i > 0; i--) {
                outputFile.writeInt(i);
            }
        }
    }

    private int[] readOutputFile() throws IOException {
        int[] result = new int[200];
        int index = 0;

        try (RandomAccessFile input = new RandomAccessFile(
            outputFile,
            "r"
        )) {
            while (true) {
                result[index++] = input.readInt();
            }
        } catch (EOFException ignored) {
        }

        return Arrays.copyOf(result, index - 1);
    }

    private void createTmpFiles() throws IOException {
        OngoingStubbing<File> tmpFileProviderMock = when(tmpFileProvider.create());
        for (int index = 1; index <= 10; index++) {
            tmpFileProviderMock = tmpFileProviderMock
                .thenReturn(folder.newFile("tmp" + index));
        }
    }
}