package server.commands;

import server.core.Invoker;
import shared.commands.enums.DataField;

import java.util.Map;

/**
 * The class contains an implementation of the add command
 */
public class AddCommand extends Command {

    public AddCommand(Invoker invoker){
        super(invoker);
    }

    @Override
    public String execute(Object arguments) {
        Map<DataField,Object> data = (Map<DataField, Object>) arguments;
        if(invoker.getModelsManager().addModels(invoker.getModelsManager().createModel(data, invoker.getPrinter()))){
            return "Object was successfully created!";
        }
        return "Object wasn't created.";
    }

    @Override
    public String getCommandInfo(){
        return String.format("Command \"add\": This command allows you to create a new music group model. When you enter the command, you will be prompted for fields to enter.");
    }
}
