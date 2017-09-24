package pega.command.builder;

import pega.command.Command;
import pega.command.MergeCommand;
import pega.command.SortCommand;
import pega.io.FileInputProvider;
import pega.io.FileIterableInputProvider;
import pega.io.FileIterableOutputProvider;
import pega.io.FileOutputProvider;
import pega.util.TmpFileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MergeSortCommandsBuilder {

    private int maxMemory;
    private File inputFile;
    private File outputFile;
    private int inputSize;
    private TmpFileProvider tmpFileProvider;

    public MergeSortCommandsBuilder(int maxMemory, File inputFile, File outputFile, int inputSize, TmpFileProvider tmpFileProvider) {
        this.maxMemory = maxMemory;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.inputSize = inputSize;
        this.tmpFileProvider = tmpFileProvider;
    }

    public List<Command> build() {
        ArrayList<Command> commands = new ArrayList<>();

        int partsToSort = (int) Math.ceil((double)inputSize / (double)maxMemory);

        if (partsToSort == 1) {
            commands.add(new SortCommand(
                new FileInputProvider(
                    inputFile,
                    0,
                    inputSize
                ),
                new FileOutputProvider(outputFile)
            ));
        } else {
            List<SortCommand> sortCommands = new ArrayList<>();
            List<File> sortCommandsOutputFiles = new ArrayList<>();

            for (int index = 0; index < partsToSort; index++) {
                File output = tmpFileProvider.create();
                sortCommandsOutputFiles.add(output);

                int startPosition = index * maxMemory;
                int inputSize = this.inputSize - (index * maxMemory);
                if (inputSize > maxMemory) {
                    inputSize = maxMemory;
                }

                sortCommands.add(new SortCommand(
                    new FileInputProvider(
                        inputFile,
                        startPosition,
                        inputSize
                    ),
                    new FileOutputProvider(output)
                ));
            }

            commands.addAll(sortCommands);
            commands.addAll(createMergeCommands(sortCommandsOutputFiles));
        }


        return commands;
    }

    private List<MergeCommand> createMergeCommands(List<File> inputs) {
        List<MergeCommand> mergeCommands = new ArrayList<>();

        if (inputs.size() == 2) {
            mergeCommands.add(new MergeCommand(
                new FileIterableInputProvider(inputs.get(0)),
                new FileIterableInputProvider(inputs.get(1)),
                new FileIterableOutputProvider(outputFile)
            ));
        } else {
            List<File> outputFiles = new ArrayList<>();
            for (int i = 0; i < inputs.size() / 2; i++) {
                File tmpOutputFile = tmpFileProvider.create();
                outputFiles.add(tmpOutputFile);

                mergeCommands.add(new MergeCommand(
                    new FileIterableInputProvider(inputs.get(i)),
                    new FileIterableInputProvider(inputs.get(i + 1)),
                    new FileIterableOutputProvider(tmpOutputFile)
                ));
            }

            if (inputs.size() % 2 == 1) {
                outputFiles.add(inputs.get(inputs.size() - 1));
            }

            if (outputFiles.size() > 1) {
                mergeCommands.addAll(createMergeCommands(outputFiles));
            }
        }

        return mergeCommands;
    }
}
