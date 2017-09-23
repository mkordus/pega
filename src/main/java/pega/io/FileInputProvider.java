package pega.io;

import java.io.*;

public class FileInputProvider implements InputProvider {

    private final File file;
    private final int startPosition;
    private final int numberOfElementsToRead;
    private final int intSize = 4;

    public FileInputProvider(File file, Integer startPosition, Integer numberOfElementsToRead) {

        this.file = file;
        this.startPosition = startPosition;
        this.numberOfElementsToRead = numberOfElementsToRead;
    }

    @Override
    public int[] getInput() throws IOException {
        int[] buffer = new int[numberOfElementsToRead];

        try (RandomAccessFile input = new RandomAccessFile(file, "r")) {
            input.seek(startPosition * intSize);

            for (int index = 0; index < numberOfElementsToRead; index++) {
                buffer[index] = input.readInt();
            }
        }

        return buffer;
    }
}
