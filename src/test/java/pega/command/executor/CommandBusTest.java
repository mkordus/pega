package pega.command.executor;

import org.junit.Before;
import org.junit.Test;
import pega.command.Command;
import pega.command.executor.CommandBus.ExecutorNotFoundException;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class CommandBusTest {

    private CommandBus commandBus;
    private CommandExecutor first;
    private CommandExecutor second;
    private Command command;

    @Before
    public void setUp() {
        first = mock(CommandExecutor.class);
        second = mock(CommandExecutor.class);
        command = mock(Command.class);

        commandBus = new CommandBus(Arrays.asList(first, second));
    }

    @Test
    public void testFirstExecutorCanExecuteCommand() throws ExecutorNotFoundException, IOException, ExecutionException, InterruptedException {
        given(first.canExecute(command)).willReturn(true);
        given(second.canExecute(command)).willReturn(false);

        commandBus.execute(command);

        verify(first, times(1)).execute(command);
        verify(second, never()).execute(any());
    }

    @Test
    public void testSecondExecutorCanExecuteCommand() throws ExecutorNotFoundException, IOException, ExecutionException, InterruptedException {
        given(first.canExecute(command)).willReturn(false);
        given(second.canExecute(command)).willReturn(true);

        commandBus.execute(command);

        verify(first, never()).execute(any());
        verify(second, times(1)).execute(command);
    }

    @Test(expected = ExecutorNotFoundException.class)
    public void testNoMatchingExecutorFound() throws ExecutorNotFoundException, IOException, ExecutionException, InterruptedException {
        given(first.canExecute(command)).willReturn(false);
        given(second.canExecute(command)).willReturn(false);

        commandBus.execute(command);
    }
}