package pega.command.builder;

import org.junit.Test;
import pega.command.Command;
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

        List<Command> result = builder.build();

        assertEquals(1, result.size());
        assertEquals(result.get(0).getClass(), SortCommand.class);
    }
}