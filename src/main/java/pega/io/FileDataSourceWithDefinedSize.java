package pega.io;

import java.io.*;

public class FileDataSourceWithDefinedSize implements DataSourceWithDefinedSize {

    private final File file;
    private final int startPosition;
    private final int numberOfElementsToRead;
    private final int intSize = 4;
    private RandomAccessFile input;
    private int counter = 0;

    public FileDataSourceWithDefinedSize(
        File file,
        int startPosition,
        int numberOfElementsToRead
    ) {
        this.file = file;
        this.startPosition = startPosition;
        this.numberOfElementsToRead = numberOfElementsToRead;
    }

    @Override
    public int getNext() throws IOException {
        if (input == null) {
            input = new RandomAccessFile(file, "r");
            input.seek(startPosition * intSize);
        }

        if (counter == numberOfElementsToRead) {
            throw new EOFException();
        }
        counter++;

        return input.readInt();
    }

    public void close() throws IOException {
        if (input != null) {
            input.close();
        }
    }

    public int getSize() {
        return numberOfElementsToRead;
    }
}
