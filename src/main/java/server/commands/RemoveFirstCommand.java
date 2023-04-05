package server.commands;

import server.core.Invoker;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;

/**
 * The class contains an implementation of the remove_first command
 */
public class RemoveFirstCommand extends Command{

    public RemoveFirstCommand(Invoker invoker) {
        super(invoker);
    }

    @Override
    public String execute(Object args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {
        if (invoker.getModelsManager().getModels().isEmpty()){
            return "Models collection is empty!";
        }
        invoker.getModelsManager().removeById(invoker.getModelsManager().getModels().getFirst().getId(), invoker.getPrinter());
        return "Remove command executed!";
    }

    @Override
    public String getCommandInfo() {
        return String.format("Command \"remove_first\": This command removes the first model in the collection.");
    }
}
