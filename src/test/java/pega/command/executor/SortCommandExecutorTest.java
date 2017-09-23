package pega.command.executor;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pega.command.SortCommand;
import pega.io.InputProvider;
import pega.io.OutputProvider;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(DataProviderRunner.class)
public class SortCommandExecutorTest {

    private InputProvider inputProvider;
    private OutputProvider outputProvider;
    private SortCommandExecutor executor = new SortCommandExecutor();
    private SortCommand command;

    @Before
    public void setUp() {
        inputProvider = mock(InputProvider.class);
        outputProvider = mock(OutputProvider.class);
        command = new SortCommand(inputProvider, outputProvider);
    }

    @Test
    @UseDataProvider("dataProvider")
    public void executeTest(int[] input, int[] output) {
        given(inputProvider.getInput())
            .willReturn(input);

        executor.execute(command);

        verify(outputProvider, times(1))
            .setOutput(eq(output));
    }

    @DataProvider
    public static Object[][] dataProvider() {
        return new Object[][] {
            {
                new int[] {3, 2, 1},
                new int[] {1, 2, 3},
            },
            {
                new int[] {5, 3, 2, 1, 4, 6},
                new int[] {1, 2, 3, 4, 5, 6},
            },
        };
    }
}