package client.commands;

import client.connection.ThreadsBridgeHandler;
import client.core.Invoker;
import shared.commands.commandsdtos.CommandDTO;
import shared.connection.requests.CommandRequest;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;

/**
 * The class contains an implementation of the remove_head command
 */
public class RemoveHeadCommand extends Command{
    private final Invoker invoker;

    public RemoveHeadCommand(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public void execute(String... args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {
        invoker.getConnection().getSender().send(new CommandRequest(new CommandDTO("RemoveHeadCommand"), null));
        ThreadsBridgeHandler.waitCommandExecuted(invoker.getPipedInputStream(), invoker.getPrinter());
    }
}
