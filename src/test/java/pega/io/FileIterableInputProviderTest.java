package pega.io;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FileIterableInputProviderTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private File file;

    @Before
    public void setUp() throws IOException {
        file = folder.newFile("test");

        try (RandomAccessFile accessFile = new RandomAccessFile(
            file,
            "rw"
        )) {
            for (int i = 1; i <= 10; i++) {
                accessFile.writeInt(i);
            }
        }
    }

    @Test
    public void testHappyPath() throws Exception {
        int[] output = new int[10];
        int index = 0;

        try (FileIterableInputProvider input = new FileIterableInputProvider( file )) {
            while(true) {
                output[index++] = input.getNext();
            }
        } catch (EOFException ignored) {
        }

        assertArrayEquals(
            IntStream.rangeClosed(1, 10).toArray(),
            output
        );
    }
}