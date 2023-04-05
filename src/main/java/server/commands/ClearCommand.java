package server.commands;

import server.core.Invoker;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;

/**
 * The class contains an implementation of the clear command
 */
public class ClearCommand extends Command{
    public ClearCommand(Invoker invoker) {
        super(invoker);
    }

    @Override
    public String execute(Object args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {
        invoker.getModelsManager().removeAll(invoker.getPrinter());
        return "Models cleared!";
    }

    @Override
    public String getCommandInfo() {
        return "Command \"clear\": This command remove all models from collection.";
    }
}
