package pega;

import pega.command.SortCommand;
import pega.command.executor.SortCommandExecutor;
import pega.io.FileInputProvider;
import pega.io.FileOutputProvider;

import java.io.File;
import java.io.IOException;

public class MergeSortFile {

    private final File inputFile;
    private final File outputFile;
    private final int inputSize;

    public static void main(String... args) throws IOException {
        File inputFile = new File(args[0]);
        Integer inputSize = Integer.valueOf(args[1]);
        File outputFile = new File(args[2]);
        Integer maxMemory = Integer.valueOf(args[3]);
        Integer cores = Runtime.getRuntime().availableProcessors();

        MergeSortFile mergeSort = new MergeSortFile(
            inputFile,
            outputFile,
            inputSize
        );

        mergeSort.sort();
    }

    public MergeSortFile(File inputFile, File outputFile, int inputSize) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.inputSize = inputSize;
    }

    public void sort() throws IOException {
        SortCommand command = new SortCommand(
            new FileInputProvider(
                inputFile,
                0,
                inputSize
            ),
            new FileOutputProvider(
                outputFile,
                false
            )
        );

        SortCommandExecutor executor = new SortCommandExecutor();
        executor.execute(command);
    }
}
