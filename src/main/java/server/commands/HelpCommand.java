package server.commands;

import server.core.Invoker;

import java.util.Map;

/**
 * The class contains an implementation of the help command
 */
public class HelpCommand extends Command {

    public HelpCommand(Invoker invoker){
        super(invoker);
    }

    @Override
    public String execute(Object arguments) {
        Map<String, Command> commands = invoker.getCommandsManager().getCommandsCollection();
        for (Map.Entry<String, Command> entry : commands.entrySet()){
            invoker.getPrinter().print(commands.get(entry.getKey()).getCommandInfo());
        }
        return "Command \"help\" executed!";
    }

    @Override
    public String getCommandInfo() {
        return String.format("Command \"help\": This command outputs information about all the other commands.");
    }

}
