package pega.io;

import java.io.IOException;

public interface OutputProvider {
    void write(int[] output) throws IOException;
}
