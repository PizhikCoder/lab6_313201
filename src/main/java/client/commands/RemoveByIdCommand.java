package client.commands;

import client.connection.ThreadsBridgeHandler;
import client.core.Invoker;
import shared.commands.commandsdtos.CommandDTO;
import shared.connection.requests.CommandRequest;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;
import client.core.validators.CommandsDataValidator;

/**
 * The class contains an implementation of the remove_by_id command
 */
public class RemoveByIdCommand extends Command{
    private final Invoker invoker;
    private final int EXPECTED_ARGUMENTS_COUNT = 1;
    private final int ID_INDEX = 0;
    public RemoveByIdCommand(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public void execute(String... args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {
        if (args.length == 0){
            throw new CommandParamsException(0, EXPECTED_ARGUMENTS_COUNT);
        }
        long id = (long)CommandsDataValidator.numbersCheck(args[ID_INDEX], invoker.getListener(),invoker.getPrinter(), Long.class, false);
        invoker.getConnection().getSender().send(new CommandRequest(new CommandDTO("RemoveByIdCommand"), id));
        ThreadsBridgeHandler.waitCommandExecuted(invoker.getPipedInputStream(), invoker.getPrinter());
    }
}
