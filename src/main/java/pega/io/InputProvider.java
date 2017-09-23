package pega.io;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface InputProvider {
    int[] getInput() throws IOException;
}
