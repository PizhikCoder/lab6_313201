package server.commands;

import server.core.Invoker;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;

/**
 * The class contains an implementation of the remove_head command
 */
public class RemoveHeadCommand extends Command{

    public RemoveHeadCommand(Invoker invoker) {
        super(invoker);
    }

    @Override
    public String execute(Object args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {
        if (invoker.getModelsManager().getModels().isEmpty()){
            return "Models collection is empty!";
        }
        invoker.getPrinter().print(invoker.getModelsManager().getModels().getFirst().toString());
        invoker.getModelsManager().removeById(invoker.getModelsManager().getModels().getFirst().getId(), invoker.getPrinter());
        return "Remove head command executed!";
    }

    @Override
    public String getCommandInfo() {
        return String.format("Command \"remove_head\": This command shows the first model in the collection and than removes it.");
    }
}
