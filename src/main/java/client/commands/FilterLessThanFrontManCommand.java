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
 * The class contains an implementation of the filter_less_the_front_man command
 */
public class FilterLessThanFrontManCommand extends Command{
    private final Invoker invoker;
    private final int EXPECTED_ARGUMENTS_COUNT = 1;


    public FilterLessThanFrontManCommand(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public void execute(String... args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {
        if (args.length == 0){
            throw new CommandParamsException(0, EXPECTED_ARGUMENTS_COUNT);
        }
        Validator<Float> heightValidator = n->n==null || n>0;
        float height = (float) CommandsDataValidator.numbersCheck(args[0], invoker.getListener(),invoker.getPrinter(), Float.class, false, heightValidator);

        invoker.getConnection().getSender().send(new CommandRequest(new CommandDTO("FilterLessThanFrontManCommand"), height));
        ThreadsBridgeHandler.waitCommandExecuted(invoker.getPipedInputStream(), invoker.getPrinter());
    }
}
