package pega;

import pega.io.FileDataSource;
import pega.io.DataSource;

import java.io.*;

public class DecodeIntBinaryFile {

    public static void main(String... args) throws Exception {
        File input = new File(args[0]);
        File output = new File(args[1]);

        DataSource inputProvider = new FileDataSource(input);
        RandomAccessFile randomAccessFile = new RandomAccessFile(output, "rw");

        try {
            while (true) {
                randomAccessFile.writeChars(
                    String.valueOf(inputProvider.getNext()) + "\n"
                );
            }
        } catch (EOFException ignored) {
        } finally {
            inputProvider.close();
            randomAccessFile.close();
        }
    }
}
