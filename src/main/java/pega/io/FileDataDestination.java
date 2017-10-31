package pega.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class FileDataDestination implements DataDestination {

    private final File file;
    private int size;
    private RandomAccessFile output;
    private CompletableFuture<Object> future = new CompletableFuture<>();

    public FileDataDestination(File file) {
        this.file = file;
    }

    @Override
    public void write(int element) throws IOException {
        if (output == null) {
            output = new RandomAccessFile(file, "rw");
            output.seek(output.length());
        }

        output.writeInt(element);
        size++;
    }

    @Override
    public void close() throws IOException {
        if (output != null) {
            future.complete(new Object());
            output.close();
        }
    }

    @Override
    public DataSource createDataSource() {
        return new FileDataSource(this);
    }

    @Override
    public int getSize() {
        return size;
    }

    File getFile() {
        return file;
    }

    Future<Object> getFuture() {
        return future;
    }

    @Override
    public String toString() {
        return "FileDataDestination: " + file.getPath();
    }
}
