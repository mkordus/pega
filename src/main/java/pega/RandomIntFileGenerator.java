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

        try (OutputStream outputStream = new FileOutputStream(file)) {
            DataOutputStream output = new DataOutputStream(
                new BufferedOutputStream(outputStream)
            );

            for (int index = 0; index < numberOfElements; index++) {
                output.writeInt(ThreadLocalRandom.current().nextInt());
            }

            output.flush();
        }
    }
}
