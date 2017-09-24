package pega.io;

import java.io.*;

public class FileIterableInputProvider implements IterableInputProvider {

    private final File file;
    private final int startPosition;
    private final int numberOfElementsToRead;
    private final Mode mode;
    private final int intSize = 4;
    private RandomAccessFile input;
    private int counter = 0;

    public FileIterableInputProvider(File file) {
        this.file = file;
        startPosition = 0;
        numberOfElementsToRead = 0;
        mode = Mode.READ_ALL;
    }

    public FileIterableInputProvider(
        File file,
        int startPosition,
        int numberOfElementsToRead
    ) {
        this.file = file;
        this.startPosition = startPosition;
        this.numberOfElementsToRead = numberOfElementsToRead;
        mode = Mode.READ_NUMBER_OF_ELEMENTS;
    }

    @Override
    public int getNext() throws IOException {
        if (input == null) {
            input = new RandomAccessFile(file, "r");
            if (mode == Mode.READ_NUMBER_OF_ELEMENTS) {
                input.seek(startPosition * intSize);
            }
        }

        if (mode == Mode.READ_NUMBER_OF_ELEMENTS) {
            if (counter == numberOfElementsToRead) {
                throw new EOFException();
            }
            counter++;
        }

        return input.readInt();
    }

    public void close() throws IOException {
        if (input != null) {
            input.close();
        }
    }

    private enum Mode {
        READ_ALL,
        READ_NUMBER_OF_ELEMENTS
    }
}
