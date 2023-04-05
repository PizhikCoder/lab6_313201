package server.commands;

import server.core.ClonesParser;
import server.core.Invoker;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.RecursionException;

/**
 * The class contains an implementation of the save command
 */
public class ExitCommand extends Command {
    public ExitCommand(Invoker invoker) {
        super(invoker);
    }

    @Override
    public String execute(Object arguments) throws RecursionException, FileAccessException {
        if (invoker.getDataSaver().save(ClonesParser.toClones(ClonesParser.dequeToArray(invoker.getModelsManager().getModels())))){
            return "Models were saved.";

        }
        return "Models weren't saved.";
    }

    @Override
    public String getCommandInfo() {
        return "Command \"exit\": This command terminates the programme by prompting you to save the changes.";
    }
}
