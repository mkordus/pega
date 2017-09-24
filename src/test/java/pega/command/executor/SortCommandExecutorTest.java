package pega.command.executor;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.OngoingStubbing;
import pega.command.SortCommand;
import pega.io.DataSource;
import pega.io.DataSourceWithDefinedSize;
import pega.io.DataDestination;

import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(DataProviderRunner.class)
public class SortCommandExecutorTest {

    public ArgumentCaptor<Integer> captor;
    private DataSourceWithDefinedSize inputProvider;
    private DataDestination outputProvider;
    private SortCommandExecutor executor = new SortCommandExecutor();
    private SortCommand command;

    @Before
    public void setUp() {
        inputProvider = mock(DataSourceWithDefinedSize.class);
        outputProvider = mock(DataDestination.class);
        command = new SortCommand(inputProvider, outputProvider);
        captor = ArgumentCaptor.forClass(Integer.class);
    }

    @Test
    @UseDataProvider("dataProvider")
    public void executeTest(int[] input, Integer[] expectedOutput) throws IOException, ExecutionException, InterruptedException {
        when(inputProvider.getSize()).thenReturn(input.length);
        buildMock(inputProvider, input);

        executor.execute(command);

        verify(outputProvider, times(input.length))
            .write(captor.capture());

        assertEquals(Arrays.asList(expectedOutput), captor.getAllValues());

        verify(inputProvider).close();
        verify(outputProvider).close();
    }

    @DataProvider
    public static Object[][] dataProvider() {
        return new Object[][] {
            {
                new int[] {3, 2, 1},
                new Integer[] {1, 2, 3},
            },
            {
                new int[] {5, 3, 2, 1, 4, 6},
                new Integer[] {1, 2, 3, 4, 5, 6},
            },
        };
    }

    private void buildMock(DataSource provider, int[] input) throws IOException, ExecutionException, InterruptedException {
        OngoingStubbing<Integer> providerMock = when(provider.getNext());
        for (int item : input) {
            providerMock = providerMock.thenReturn(item);
        }
        providerMock.thenThrow(new EOFException());
    }
}