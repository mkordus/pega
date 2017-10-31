package pega.io;

import java.io.*;
import java.util.concurrent.ExecutionException;

public class FileDataSource implements DataSource {

    private final File file;
    private final int startPosition;
    private Integer numberOfElementsToRead;
    private RandomAccessFile input;
    private FileDataDestination destination;
    private int counter = 0;

    public FileDataSource(
        File file,
        int startPosition,
        int numberOfElementsToRead
    ) {
        this.file = file;
        this.startPosition = startPosition;
        this.numberOfElementsToRead = numberOfElementsToRead;
    }

    public FileDataSource(
        File file
    ) {
        this.file = file;
        this.startPosition = 0;
    }

    public FileDataSource(FileDataDestination destination) {
        this.destination = destination;
        this.file = destination.getFile();
        this.startPosition = 0;
    }

    private void init() throws ExecutionException, InterruptedException, IOException {
        if (destination != null) {
            destination.getFuture().get();
        }
        input = new RandomAccessFile(file, "r");
        input.seek(startPosition * Integer.BYTES);

        if (numberOfElementsToRead == null) {
            numberOfElementsToRead = Math.toIntExact(
                input.length() / Integer.BYTES - startPosition
            );
        }
    }

    @Override
    public int getNext() throws ExecutionException, InterruptedException, IOException {
        if (input == null) {
            init();
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

    @Override
    public Integer getSize() {
        return numberOfElementsToRead;
    }

    @Override
    public String toString() {
        return "FileDataSource: " + file.getPath();
    }
}
