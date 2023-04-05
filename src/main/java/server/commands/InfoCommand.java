package server.commands;

import server.core.Invoker;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;

/**
 * The class contains an implementation of the info command
 */
public class InfoCommand extends Command{

    public InfoCommand(Invoker invoker) {
        super(invoker);
    }

    @Override
    public String execute(Object args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {
        return String.format("Collection info:" +
                "\n---Type: MusicBand" +
                "\n---Date of initialization: %s" +
                "\n---Elements count: %s", invoker.getModelsManager().getCreationDate(), invoker.getModelsManager().getModels().size());
    }

    @Override
    public String getCommandInfo() {
        return "Command \"info\": This command outputs information about the collection (type, initialisation date, number of items)";
    }
}
