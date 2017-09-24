package pega.command.builder;

import org.junit.Before;
import org.junit.Test;
import pega.command.Command;
import pega.command.MergeCommand;
import pega.command.SortCommand;
import pega.util.TmpFileProvider;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MergeSortCommandsBuilderTest {

    private File inputFile = mock(File.class);
    private File outputFile = mock(File.class);
    private TmpFileProvider tmpFileProvider;

    @Before
    public void setUp() {
        tmpFileProvider = mock(TmpFileProvider.class);
        when(tmpFileProvider.create()).thenReturn(mock(File.class ));
    }

    @Test
    public void itShouldReturnSortCommandIfInputFitsIntoMemory() {
        int maxMemory = 100;
        int inputSize = 100;

        MergeSortCommandsBuilder builder = new MergeSortCommandsBuilder(
            maxMemory,
            inputFile,
            outputFile,
            inputSize,
            tmpFileProvider
        );

        List<? extends Command> result = builder.build();

        assertEquals(1, result.size());
        assertEquals(result.get(0).getClass(), SortCommand.class);
    }

    @Test
    public void itShouldReturnMultipleSortCommandsIfInputIsLargerThanMemory() {
        int maxMemory = 50;
        int inputSize = 100;

        List<? extends Command> result = new MergeSortCommandsBuilder(
            maxMemory,
            inputFile,
            outputFile,
            inputSize,
            tmpFileProvider
        ).build();

        assertTrue(result.size() >= 2);
        assertEquals(result.get(0).getClass(), SortCommand.class);
        assertEquals(result.get(1).getClass(), SortCommand.class);
    }

    @Test
    public void itShouldMergeOutputsIfInputIsLargerThanMemory() {
        int maxMemory = 50;
        int inputSize = 100;

        List<? extends Command> result = new MergeSortCommandsBuilder(
            maxMemory,
            inputFile,
            outputFile,
            inputSize,
            tmpFileProvider
        ).build();

        assertTrue(result.size() == 3);
        assertEquals(result.get(2).getClass(), MergeCommand.class);
    }


    @Test
    public void itShouldMergeThreeSortedInputs() {
        int maxMemory = 50;
        int inputSize = 150;

        List<? extends Command> result = new MergeSortCommandsBuilder(
            maxMemory,
            inputFile,
            outputFile,
            inputSize,
            tmpFileProvider
        ).build();

        assertTrue(result.size() == 5);
        assertEquals(result.get(0).getClass(), SortCommand.class);
        assertEquals(result.get(1).getClass(), SortCommand.class);
        assertEquals(result.get(2).getClass(), SortCommand.class);
        assertEquals(result.get(3).getClass(), MergeCommand.class);
        assertEquals(result.get(4).getClass(), MergeCommand.class);
    }

    @Test
    public void itShouldMergeFourSortedInputs() {
        int maxMemory = 50;
        int inputSize = 200;

        List<? extends Command> result = new MergeSortCommandsBuilder(
            maxMemory,
            inputFile,
            outputFile,
            inputSize,
            tmpFileProvider
        ).build();

        assertTrue(result.size() == 7);
        assertEquals(result.get(0).getClass(), SortCommand.class);
        assertEquals(result.get(1).getClass(), SortCommand.class);
        assertEquals(result.get(2).getClass(), SortCommand.class);
        assertEquals(result.get(3).getClass(), SortCommand.class);
        assertEquals(result.get(4).getClass(), MergeCommand.class);
        assertEquals(result.get(5).getClass(), MergeCommand.class);
        assertEquals(result.get(6).getClass(), MergeCommand.class);
    }
}