package server.commands;

import server.core.Invoker;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;

/**
 * The class contains an implementation of the count_greater_than_front_man command
 */
public class CountGreaterThanFrontManCommand extends Command{

    public CountGreaterThanFrontManCommand(Invoker invoker) {
       super(invoker);
    }

    @Override
    public String execute(Object args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {
        if (invoker.getModelsManager().getModels().isEmpty()){
            return "Collection is empty!";
        }
        float height = (float) args;

        long count = invoker.getModelsManager().getModels().stream()
                .filter(x->x.getFrontMan().getHeight()!=null
                        && x.getFrontMan().getHeight()>height).count();

        invoker.getPrinter().print(String.format("Music bands with front men, that higher than %s: %s", height, count));

        return "Count Greater Than Front Man Command executed!";
    }

    @Override
    public String getCommandInfo() {
        return String.format("Command \"count_greater_than_front_man <height>\": This command allows you to count items whose frontMan field value is greater than the set value."
        +"\nArguments: Float (not null, >0)");
    }
}
