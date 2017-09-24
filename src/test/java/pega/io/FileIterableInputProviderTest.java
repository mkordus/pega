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
    public void testGetAll() throws Exception {
        int[] output = new int[10];
        int index = 0;

        FileIterableInputProvider input;
        try {
            input = new FileIterableInputProvider(file);
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

    @Test
    public void testGetDataToMiddle() throws IOException {
        FileIterableInputProvider provider = new FileIterableInputProvider(
            file,
            0,
            5
        );

        for (int element : new int[] {1, 2 ,3 ,4 ,5}) {
            assertEquals(element, provider.getNext());
        }

        boolean exceptionWasThrown = false;
        try {
            provider.getNext();
        } catch (EOFException ignored) {
            exceptionWasThrown = true;
        }

        assertTrue(exceptionWasThrown);
    }

    @Test
    public void testGetDataFromMiddle() throws IOException {
        FileIterableInputProvider provider = new FileIterableInputProvider(
            file,
            5,
            5
        );

        for (int element : new int[]{6, 7, 8, 9, 10}) {
            assertEquals(element, provider.getNext());
        }

        boolean exceptionWasThrown = false;
        try {
            provider.getNext();
        } catch (EOFException ignored) {
            exceptionWasThrown = true;
        }

        assertTrue(exceptionWasThrown);
    }
}