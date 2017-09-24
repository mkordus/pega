package pega.util;

import java.io.File;

public class TmpFileProvider {

    private int tmpFileCounter = 1;

    public File create() {
        return new File("tmp" + tmpFileCounter++);
    }
}
