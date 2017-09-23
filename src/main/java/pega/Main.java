package pega;

import java.io.File;

public class Main {
    public static void main(String... args) {
        File inputFile = new File(args[0]);
        Long inputSize = Long.valueOf(args[1]);
        File outputFile = new File(args[2]);
        Long maxMemory = Long.valueOf(args[3]);

        Integer cores = Runtime.getRuntime().availableProcessors();
    }
}
