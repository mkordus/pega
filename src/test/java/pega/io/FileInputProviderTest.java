package pega.io;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FileInputProviderTest {

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
            for (int i = 0; i < 10; i++) {
                accessFile.writeInt(i + 1);
            }
        }
    }

    @Test
    public void testGetAllData() throws IOException {
        FileInputProvider provider = new FileInputProvider(
            file,
            0,
            10
        );

        int[] expectedResult = {1, 2 ,3 ,4 ,5, 6, 7, 8, 9, 10};
        assertArrayEquals(expectedResult, provider.getInput());
    }

    @Test
    public void testGetDataToMiddle() throws IOException {
        FileInputProvider provider = new FileInputProvider(
            file,
            0,
            5
        );

        int[] expectedResult = {1, 2 ,3 ,4 ,5};
        assertArrayEquals(expectedResult, provider.getInput());
    }

    @Test
    public void testGetDataFromMiddle() throws IOException {
        FileInputProvider provider = new FileInputProvider(
            file,
            5,
            5
        );

        int[] expectedResult = {6, 7, 8, 9, 10};
        assertArrayEquals(expectedResult, provider.getInput());
    }
}