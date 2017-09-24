package pega.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileIterableOutputProvider implements IterableOutputProvider {

    private final File file;
    private RandomAccessFile output;

    public FileIterableOutputProvider(File file) {
        this.file = file;
    }

    @Override
    public void write(int element) throws IOException {
        if (output == null) {
            output = new RandomAccessFile(file, "rw");
            output.seek(output.length());
        }

        output.writeInt(element);
    }

    @Override
    public void close() throws IOException {
        if (output != null) {
            output.close();
        }
    }

    @Override
    public IterableInputProvider createInput() {
        return new FileIterableInputProvider(file);
    }
}
