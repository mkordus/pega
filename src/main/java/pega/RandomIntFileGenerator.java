package pega;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomIntFileGenerator {

    public static void main(String... args) throws Exception {
        File file = new File(args[0]);
        Long numberOfElements = Long.valueOf(args[1]);
        System.out.print(
            file.getAbsolutePath() + " [size: " + numberOfElements + "]"
        );

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")) {
            randomAccessFile.setLength(0);

            for (int index = 0; index < numberOfElements; index++) {
                randomAccessFile.writeInt(ThreadLocalRandom.current().nextInt());
            }
        }
    }
}
