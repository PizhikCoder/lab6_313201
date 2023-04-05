package server.commands;

import server.core.Invoker;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;
import shared.core.models.MusicBand;

/**
 * The class contains an implementation of the show command
 */
public class ShowCommand extends Command{

    public ShowCommand(Invoker invoker){
        super(invoker);
    }

    @Override
    public String execute(Object args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {
        invoker.getModelsManager().getModels().stream().map(MusicBand::toString).forEach(invoker.getPrinter()::print);
        return "Command \"show\" executed!";
    }

    @Override
    public String getCommandInfo() {
        return "Command \"show\": This command print all elements of collection.";
    }
}
