package pega;

import pega.command.Command;
import pega.command.builder.MergeSortCommandsBuilder;
import pega.command.executor.CommandBus;
import pega.command.executor.CommandBus.ExecutorNotFoundException;
import pega.command.executor.MergeCommandExecutor;
import pega.command.executor.SortCommandExecutor;
import pega.util.TmpFileProvider;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MergeSortFile {

    private final File inputFile;
    private final File outputFile;
    private final int inputSize;
    private final int maxMemory;
    private final int numberOfThreads;
    private final CommandBus commandBus;
    private final TmpFileProvider tmpFileProvider;

    public static void main(String... args) throws IOException, ExecutorNotFoundException {
        File inputFile = new File(args[0]);
        Integer inputSize = Integer.valueOf(args[1]);
        File outputFile = new File(args[2]);
        Integer maxMemory = Integer.valueOf(args[3]);

        CommandBus commandBus = new CommandBus(Arrays.asList(
            new SortCommandExecutor(),
            new MergeCommandExecutor()
        ));

        MergeSortFile mergeSort = new MergeSortFile(
            commandBus,
            inputFile,
            outputFile,
            inputSize,
            maxMemory,
            new TmpFileProvider(),
            4
        );

        mergeSort.sort();
    }

    public MergeSortFile(CommandBus commandBus, File inputFile, File outputFile, int inputSize, int maxMemory, TmpFileProvider tmpFileProvider, int numberOfThreads) {
        this.commandBus = commandBus;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.inputSize = inputSize;
        this.maxMemory = maxMemory;
        this.tmpFileProvider = tmpFileProvider;
        this.numberOfThreads = numberOfThreads;
    }

    public void sort() throws IOException, ExecutorNotFoundException {
        List<Command> commands = new MergeSortCommandsBuilder(
            maxMemory / numberOfThreads,
            inputFile,
            outputFile,
            inputSize,
            tmpFileProvider
        ).build();

        for (Command command : commands) {
            commandBus.execute(command);
        }
    }
}
