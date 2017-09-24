package pega.command.builder;

import pega.command.Command;
import pega.command.SortCommand;
import pega.io.FileInputProvider;
import pega.io.FileOutputProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MergeSortCommandsBuilder {

    private int maxMemory;
    private File inputFile;
    private File outputFile;
    private int inputSize;

    public MergeSortCommandsBuilder(int maxMemory, File inputFile, File outputFile, int inputSize) {
        this.maxMemory = maxMemory;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.inputSize = inputSize;
    }

    public List<Command> build() {
        List<Command> commands = new ArrayList<>();

        commands.add(new SortCommand(
            new FileInputProvider(
                inputFile,
                0,
                inputSize
            ),
            new FileOutputProvider(outputFile)
        ));

        return commands;
    }
}
