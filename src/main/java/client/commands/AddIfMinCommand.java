package client.commands;

import client.connection.ThreadsBridgeHandler;
import shared.commands.commandsdtos.CommandDTO;
import shared.commands.enums.DataField;
import shared.connection.requests.CommandRequest;
import shared.connection.requests.ValidationRequest;
import client.core.Invoker;
import shared.core.exceptions.*;
import client.core.validators.CommandsDataValidator;

import java.util.Map;

/**
 * The class contains an implementation of the add_if_min command.
 */
public class AddIfMinCommand extends Command  {
    private final Invoker invoker;
    private final int ID_INDEX = 0;
    private final int EXPECTED_ARGUMENTS_COUNT = 1;

    public AddIfMinCommand(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public void execute(String... args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException, ArgumentLimitsException {
        if (args.length == 0){
            throw new CommandParamsException(0,EXPECTED_ARGUMENTS_COUNT);
        }
        long id = (long)CommandsDataValidator.numbersCheck(args[ID_INDEX], invoker.getListener(),invoker.getPrinter(), Long.class, false);
        if (id<=0){
            throw new ArgumentLimitsException(0);
        }

        invoker.getConnection().getSender().send(new ValidationRequest(new CommandDTO("AddIfMinCommand"), id));
        if (ThreadsBridgeHandler.getValidationResponse(invoker.getPipedInputStream(), invoker.getPrinter())){
            Map<DataField, Object> data = ((AddCommand)invoker.getListener().getCommandsManager().getCommandsCollection().get("add")).collectData();
            invoker.getConnection().getSender().send(new CommandRequest(new CommandDTO("AddIfMinCommand"), new Object[]{data, id}));
        }
        ThreadsBridgeHandler.waitCommandExecuted(invoker.getPipedInputStream(), invoker.getPrinter());
    }
}
