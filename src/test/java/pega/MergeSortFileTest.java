package pega;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import pega.command.executor.CommandBus;
import pega.command.executor.CommandBus.ExecutorNotFoundException;
import pega.command.executor.SortCommandExecutor;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MergeSortFileTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private File inputFile;
    private File outputFile;
    private final CommandBus commandBus = new CommandBus(Arrays.asList(
        new SortCommandExecutor()
    ));

    @Before
    public void setUp() throws IOException {
        inputFile = folder.newFile("input");
        outputFile = folder.newFile("output");

        fillInputFile();
    }

    @Test
    public void testFileFittingIntoMemory() throws IOException, ExecutorNotFoundException {
        int inputFileSize = 100;

        MergeSortFile mergeSortFile = new MergeSortFile(
            commandBus,
            inputFile,
            outputFile,
            inputFileSize,
            100
        );

        mergeSortFile.sort();

        assertArrayEquals(
            IntStream.rangeClosed(1, 100).toArray(),
            readOutputFile()
        );
    }

    private void fillInputFile() throws IOException {
        try (RandomAccessFile outputFile = new RandomAccessFile(
            inputFile,
            "rw"
        )) {
            for (int i = 100; i > 0; i--) {
                outputFile.writeInt(i);
            }
        }
    }

    private int[] readOutputFile() throws IOException {
        int[] result = new int[120];
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
}