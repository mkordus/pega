package pega.command.builder;

import org.junit.Test;
import pega.command.Command;
import pega.command.MergeCommand;
import pega.command.SortCommand;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MergeSortCommandsBuilderTest {

    private File inputFile = mock(File.class);
    private File outputFile = mock(File.class);

    @Test
    public void itShouldReturnSortCommandIfInputFitsIntoMemory() {
        int maxMemory = 100;
        int inputSize = 100;

        MergeSortCommandsBuilder builder = new MergeSortCommandsBuilder(
            maxMemory,
            inputFile,
            outputFile,
            inputSize
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
            inputSize
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
            inputSize
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
            inputSize
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
            inputSize
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