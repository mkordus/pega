package pega;

import pega.command.Command;
import pega.command.SortCommand;
import pega.command.builder.MergeSortCommandsBuilder;
import pega.command.executor.CommandBus;
import pega.command.executor.CommandBus.ExecutorNotFoundException;
import pega.command.executor.SortCommandExecutor;
import pega.io.FileInputProvider;
import pega.io.FileOutputProvider;
import pega.util.TmpFileProvider;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MergeSortFile {

    private final File inputFile;
    private final File outputFile;
    private final int inputSize;
    private final CommandBus commandBus;
    private final int maxMemory;
    private TmpFileProvider tmpFileProvider;

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
            inputSize,
            100,
            new TmpFileProvider()
        );

        mergeSort.sort();
    }

    public MergeSortFile(CommandBus commandBus, File inputFile, File outputFile, int inputSize, int maxMemory, TmpFileProvider tmpFileProvider) {
        this.commandBus = commandBus;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.inputSize = inputSize;
        this.maxMemory = maxMemory;
        this.tmpFileProvider = tmpFileProvider;
    }

    public void sort() throws IOException, ExecutorNotFoundException {
        List<Command> commands = new MergeSortCommandsBuilder(
            maxMemory,
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
