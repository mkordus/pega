package pega.command.executor;

import pega.command.Command;
import pega.command.executor.CommandBus.ExecutorNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentCommandsExecutor {

    private final CommandBus commandBus;
    private final int numberOfThreads;

    public ConcurrentCommandsExecutor(int numberOfThreads, CommandBus commandBus) {
        this.numberOfThreads = numberOfThreads;
        this.commandBus = commandBus;
    }

    public void execute(List<Command> commands) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        for (Command command : commands) {
            executorService.execute(() -> {
                try {
                    System.out.println("Starting " + command);

                    commandBus.execute(command);

                    System.out.println("Ended " + command);
                } catch (ExecutorNotFoundException | IOException |
                    ExecutionException | InterruptedException e
                ) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
    }
}
