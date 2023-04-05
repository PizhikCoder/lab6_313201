package client.commands;

import client.connection.ThreadsBridgeHandler;
import client.core.Invoker;
import shared.commands.commandsdtos.CommandDTO;
import shared.connection.requests.CommandRequest;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;
import client.core.validators.CommandsDataValidator;
import shared.interfaces.Validator;

/**
 * The class contains an implementation of the count_greater_than_front_man command
 */
public class CountGreaterThanFrontManCommand extends Command{
    private final Invoker invoker;
    private final int EXPECTED_ARGUMENTS_COUNT = 1;
    private final int HEIGHT_INDEX = 0;

    public CountGreaterThanFrontManCommand(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public void execute(String... args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {
        if (args.length == 0){
            throw new CommandParamsException(0, EXPECTED_ARGUMENTS_COUNT);
        }
        Validator<Float> heightValidator = n->n==null || n>0;
        float height = (float)CommandsDataValidator.numbersCheck(args[HEIGHT_INDEX], invoker.getListener(),invoker.getPrinter(), Float.class, false, heightValidator);

        invoker.getConnection().getSender().send(new CommandRequest(new CommandDTO("CountGreaterThanFrontManCommand"), height));
        ThreadsBridgeHandler.waitCommandExecuted(invoker.getPipedInputStream(), invoker.getPrinter());
    }
}
