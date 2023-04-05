package server.commands;
import server.core.Invoker;
import shared.commands.commandsdtos.CommandDTO;
import shared.connection.requests.ValidationRequest;
import shared.core.exceptions.*;

public abstract class Command {
    protected final Invoker invoker;

    public Command(Invoker invoker){
        this.invoker = invoker;
    }

    /**
     * The method triggers the execution of the command.
     * @param args Arguments for the command.
     * @return Summary string of command execution.
     * @throws RecursionException
     * @throws FileAccessException
     * @throws CommandParamsException
     * @throws FileDoesNotExistException
     */
    public abstract String execute(Object args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException, ArgumentLimitsException;

    /**
     * Validates the data from the user and sends the validation result back. If validation is not provided for a specific command, but such a request has been received, the value true will be returned
     * @param args
     */
    public void validate(Object args){
        invoker.getConnection().send(new ValidationRequest(new CommandDTO(""), true));
    }

    /**
     * Gets information about the command.
     * @return String with full information about the command (description, syntax, arguments and types)
     */
    public abstract String getCommandInfo();
}
