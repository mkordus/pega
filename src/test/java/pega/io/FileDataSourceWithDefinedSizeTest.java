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

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FileDataSourceWithDefinedSizeTest {

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
    public void testGetDataToMiddle() throws IOException {
        FileDataSourceWithDefinedSize provider = new FileDataSourceWithDefinedSize(
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
        FileDataSourceWithDefinedSize provider = new FileDataSourceWithDefinedSize(
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