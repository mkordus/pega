package pega;

import pega.io.FileIterableInputProvider;
import pega.io.IterableInputProvider;

import java.io.*;

public class DecodeIntBinaryFile {

    public static void main(String... args) throws Exception {
        File input = new File(args[0]);
        File output = new File(args[1]);

        IterableInputProvider inputProvider = new FileIterableInputProvider(input);
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
