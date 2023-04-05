package server.commands;

import server.core.Invoker;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;
import shared.core.models.MusicBand;

/**
 * The class contains an implementation of the filter_less_the_front_man command
 */
public class FilterLessThanFrontManCommand extends Command{


    public FilterLessThanFrontManCommand(Invoker invoker) {
        super(invoker);
    }

    @Override
    public String execute(Object args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {

        if (invoker.getModelsManager().getModels().isEmpty()){
            return "Collection is empty!";
        }
        float height = (float)args;
        invoker.getModelsManager().getModels().stream()
                .filter(x->x.getFrontMan().getHeight()!=null
                        && x.getFrontMan().getHeight()<height).map(MusicBand::toString).forEach(invoker.getPrinter()::print);

        return "Filter Less Than Front Man Command executed!";
    }

    @Override
    public String getCommandInfo() {
        return String.format("Command \"filter_less_than_front_man <height>\": This command allows you to output items whose frontMan field value is less than the set value."
        +"\nArguments: Float (not null, >0)");
    }
}
