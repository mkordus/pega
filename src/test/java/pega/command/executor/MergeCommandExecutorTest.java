package pega.command.executor;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.stubbing.OngoingStubbing;
import pega.command.MergeCommand;
import pega.io.IterableInputProvider;
import pega.io.IterableOutputProvider;

import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(DataProviderRunner.class)
public class MergeCommandExecutorTest {

    private IterableInputProvider firstInputProvider;
    private IterableInputProvider secondInputProvider;
    private IterableOutputProvider outputProvider;
    private MergeCommandExecutor executor = new MergeCommandExecutor();
    private MergeCommand command;
    public ArgumentCaptor<Integer> captor;

    @Before
    public void setUp() {
        firstInputProvider = mock(IterableInputProvider.class);
        secondInputProvider = mock(IterableInputProvider.class);
        outputProvider = mock(IterableOutputProvider.class);
        command = new MergeCommand(firstInputProvider, secondInputProvider, outputProvider);
        captor = ArgumentCaptor.forClass(Integer.class);
    }

    @Test
    @UseDataProvider("dataProvider")
    public void executeTest(int[] firstInput, int[] secondInput, Integer[] expectedOutput) throws Exception {

        buildMock(firstInputProvider, firstInput);
        buildMock(secondInputProvider, secondInput);

        executor.execute(command);

        verify(outputProvider, times(firstInput.length + secondInput.length))
            .write(captor.capture());

        assertEquals(Arrays.asList(expectedOutput), captor.getAllValues());

        verify(firstInputProvider).close();
        verify(secondInputProvider).close();
        verify(outputProvider).close();
    }

    @DataProvider
    public static Object[][] dataProvider() {
        return new Object[][] {
            {
                new int[] {4, 5, 6},
                new int[] {1, 2, 3},
                new Integer[] {1, 2, 3, 4, 5, 6},
            },
            {
                new int[] {1, 2, 3},
                new int[] {1, 2, 3},
                new Integer[] {1, 1, 2, 2, 3, 3},
            },
            {
                new int[] {1, 2, 3, 7, 9},
                new int[] {1, 2, 3, 4, 5, 6},
                new Integer[] {1, 1, 2, 2, 3, 3, 4, 5, 6, 7, 9},
            }
        };
    }

    private void buildMock(IterableInputProvider provider, int[] input) throws IOException {
        OngoingStubbing<Integer> providerMock = when(provider.getNext());
        for (int item : input) {
            providerMock = providerMock.thenReturn(item);
        }
        providerMock.thenThrow(new EOFException());
    }
}