package server.commands;


import server.core.Invoker;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;

/**
 * The class contains an implementation of the remove_by_id command
 */
public class RemoveByIdCommand extends Command {

    public RemoveByIdCommand(Invoker invoker) {
        super(invoker);
    }

    @Override
    public String execute(Object args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {

        if (invoker.getModelsManager().getModels().isEmpty()){
            return "Collection is empty!";
        }
        long id = (long)args;
        invoker.getModelsManager().removeById(id, invoker.getPrinter());
        return "Remove By Id command executed!";
    }

    @Override
    public String getCommandInfo() {
        return String.format("Command \"remove_by_id <id>\": This command removes the model with the specified id from the collection." +
                "\nArguments: Integer(>0)");
    }
}
