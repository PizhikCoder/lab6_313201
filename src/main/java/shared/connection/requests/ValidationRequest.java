package shared.connection.requests;

import shared.commands.commandsdtos.CommandDTO;
import shared.connection.interfaces.ICommandRequest;

import java.io.Serializable;

public class ValidationRequest implements ICommandRequest, Serializable {
    private CommandDTO command;
    private Object data;

    public ValidationRequest(CommandDTO command, Object data){
        this.command = command;
        this.data = data;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public CommandDTO getCommand() {
        return command;
    }
}
