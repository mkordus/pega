package pega.io;

import java.io.*;
import java.util.concurrent.ExecutionException;

public class FileDataSource implements DataSource {

    private final File file;
    private RandomAccessFile input;
    private FileDataDestination destination;

    public FileDataSource(File file) {
        this.file = file;
    }

    public FileDataSource(FileDataDestination destination) {
        this.destination = destination;
        this.file = destination.getFile();
    }

    @Override
    public int getNext() throws ExecutionException, InterruptedException, IOException {
        if (input == null) {
            if (destination != null) {
                destination.getFuture().get();
            }
            input = new RandomAccessFile(file, "r");
        }

        return input.readInt();
    }

    public void close() throws IOException {
        if (input != null) {
            input.close();
        }
    }

    @Override
    public String toString() {
        return "FileDataSource: " + file.getPath();
    }
}
