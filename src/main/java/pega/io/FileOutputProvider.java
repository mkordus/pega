package pega.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileOutputProvider implements OutputProvider {

    private final File file;
    private final boolean append;

    public FileOutputProvider(File file, boolean append) {
        this.file = file;
        this.append = append;
    }

    @Override
    public void write(int[] output) throws IOException {
        try (RandomAccessFile outputFile = new RandomAccessFile(
            file,
            "rw"
        )) {
            if (append) {
                outputFile.seek(outputFile.length());
            } else {
                outputFile.setLength(0);
            }

            for (int element : output) {
                outputFile.writeInt(element);
            }
        }
    }
}
