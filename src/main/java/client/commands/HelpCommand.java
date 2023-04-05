package client.commands;

import client.connection.ThreadsBridgeHandler;
import client.core.Invoker;
import shared.commands.commandsdtos.CommandDTO;
import shared.connection.requests.CommandRequest;

import java.util.Map;

/**
 * The class contains an implementation of the help command
 */
public class HelpCommand extends Command {
    private final Invoker invoker;
    public HelpCommand(Invoker invoker){
        this.invoker = invoker;
    }
    @Override
    public void execute(String ... arguments) {
        invoker.getConnection().getSender().send(new CommandRequest(new CommandDTO("HelpCommand"), null));
        ThreadsBridgeHandler.waitCommandExecuted(invoker.getPipedInputStream(), invoker.getPrinter());
    }

}
