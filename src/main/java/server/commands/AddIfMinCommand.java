package server.commands;

import server.core.Invoker;
import server.core.validators.ModelsValidator;
import shared.commands.enums.DataField;
import shared.connection.requests.CommandRequest;
import shared.connection.requests.ValidationRequest;
import shared.core.exceptions.*;

import java.util.Map;

/**
 * The class contains an implementation of the add_if_min command.
 */
public class AddIfMinCommand extends Command {
    private final int MAP_INDEX = 0;

    private final int ID_INDEX = 1;


    public AddIfMinCommand(Invoker invoker) {
        super(invoker);
    }

    @Override
    public String execute(Object  args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException, ArgumentLimitsException {
        if (ModelsValidator.idValueCheck((long)((Object[])args)[ID_INDEX])){
            invoker.getModelsManager().addModels(invoker.getModelsManager().createModel((Map<DataField, Object>)((Object[])args)[MAP_INDEX], (long)((Object[])args)[ID_INDEX], invoker.getPrinter()));
            invoker.getPrinter().print("Object was successfully created!");
            return "Add If Min command executed!";
        }
        return "Model with this id already exist!";
    }

    @Override
    public String getCommandInfo() {
        return String.format("Command \"add_if_min <id>\": This command creates a new collection item with the specified id if it is smaller than the smallest id in the collection."
        +"\nArguments: Integer (>0)");
    }

    @Override
    public void validate(Object args) {
        if (invoker.getModelsManager().getModels().isEmpty()){
            invoker.getPrinter().print("Collection is empty!");
        }
        else if (invoker.getModelsManager().getModels().getFirst().getId() > (long)args){
            invoker.getConnection().send(new ValidationRequest(null, true));
            return;
        }
        invoker.getPrinter().print("ID is not minimal!");
        invoker.getConnection().send(new ValidationRequest(null, false));
        invoker.getConnection().send(new CommandRequest(null, true));

    }
}
