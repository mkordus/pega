package pega;

import pega.command.Command;
import pega.command.builder.MergeSortCommandsBuilder;
import pega.command.executor.CommandBus;
import pega.command.executor.CommandBus.ExecutorNotFoundException;
import pega.command.executor.ConcurrentCommandsExecutor;
import pega.command.executor.MergeCommandExecutor;
import pega.command.executor.SortCommandExecutor;
import pega.util.TmpFileProvider;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MergeSortFile {

    private final File inputFile;
    private final File outputFile;
    private final int inputSize;
    private final int maxMemory;
    private final int numberOfThreads;
    private final ConcurrentCommandsExecutor executor;
    private final TmpFileProvider tmpFileProvider;

    public static void main(String... args) throws IOException, ExecutorNotFoundException, ExecutionException, InterruptedException {
        File inputFile = new File(args[0]);
        Integer inputSize = Integer.valueOf(args[1]);
        File outputFile = new File(args[2]);
        Integer maxMemory = Integer.valueOf(args[3]);
        int numberOfThreads = 4;

        CommandBus commandBus = new CommandBus(Arrays.asList(
            new SortCommandExecutor(),
            new MergeCommandExecutor()
        ));

        ConcurrentCommandsExecutor executor = new ConcurrentCommandsExecutor(numberOfThreads, commandBus);

        MergeSortFile mergeSort = new MergeSortFile(
            executor,
            inputFile,
            outputFile,
            inputSize,
            maxMemory,
            new TmpFileProvider(),
            numberOfThreads
        );

        mergeSort.sort();
    }

    public MergeSortFile(ConcurrentCommandsExecutor executor, File inputFile, File outputFile, int inputSize, int maxMemory, TmpFileProvider tmpFileProvider, int numberOfThreads) {
        this.executor = executor;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.inputSize = inputSize;
        this.maxMemory = maxMemory;
        this.tmpFileProvider = tmpFileProvider;
        this.numberOfThreads = numberOfThreads;
    }

    public void sort() throws IOException, ExecutorNotFoundException, ExecutionException, InterruptedException {
        List<Command> commands = new MergeSortCommandsBuilder(
            maxMemory / numberOfThreads,
            inputFile,
            outputFile,
            inputSize,
            tmpFileProvider
        ).build();

        executor.execute(commands);
    }
}
