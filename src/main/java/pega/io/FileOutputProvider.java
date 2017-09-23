package pega.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileOutputProvider implements OutputProvider {

    private final File file;

    public FileOutputProvider(File file) {
        this.file = file;
    }

    @Override
    public void write(int[] output) throws IOException {
        try (RandomAccessFile outputFile = new RandomAccessFile(
            file,
            "rw"
        )) {
            outputFile.setLength(0);

            for (int element : output) {
                outputFile.writeInt(element);
            }
        }
    }
}
