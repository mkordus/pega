package pega;

import pega.command.SortCommand;
import pega.command.executor.CommandBus;
import pega.command.executor.CommandBus.ExecutorNotFoundException;
import pega.command.executor.SortCommandExecutor;
import pega.io.FileInputProvider;
import pega.io.FileOutputProvider;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MergeSortFile {

    private final File inputFile;
    private final File outputFile;
    private final int inputSize;
    private final CommandBus commandBus;

    public static void main(String... args) throws IOException, ExecutorNotFoundException {
        File inputFile = new File(args[0]);
        Integer inputSize = Integer.valueOf(args[1]);
        File outputFile = new File(args[2]);
        Integer maxMemory = Integer.valueOf(args[3]);
        Integer cores = Runtime.getRuntime().availableProcessors();

        CommandBus commandBus = new CommandBus(Arrays.asList(
            new SortCommandExecutor()
        ));

        MergeSortFile mergeSort = new MergeSortFile(
            commandBus,
            inputFile,
            outputFile,
            inputSize
        );

        mergeSort.sort();
    }

    public MergeSortFile(CommandBus commandBus, File inputFile, File outputFile, int inputSize) {
        this.commandBus = commandBus;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.inputSize = inputSize;
    }

    public void sort() throws IOException, ExecutorNotFoundException {
        SortCommand command = new SortCommand(
            new FileInputProvider(
                inputFile,
                0,
                inputSize
            ),
            new FileOutputProvider(outputFile)
        );

        commandBus.execute(command);
    }
}
