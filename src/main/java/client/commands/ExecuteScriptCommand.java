package client.commands;

import client.core.Invoker;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;
import client.core.listeners.FileListener;
import client.core.validators.FileValidator;
import client.core.interfaces.IListener;

import java.util.LinkedList;

/**
 * The class contains an implementation of the execute_script command
 */
public class ExecuteScriptCommand extends Command {
    private String filePath;
    private IListener listener;
    private static LinkedList<IListener> listenersQueue = new LinkedList<>();
    private static boolean recursionFlag = false;
    private static LinkedList<String> pathChain = new LinkedList<>();
    private final Invoker invoker;
    private final int PATH_INDEX = 0;
    private final int EXPECTED_ARGUMENTS_COUNT = 1;

    public ExecuteScriptCommand(Invoker invoker){
        this.invoker = invoker;
    }
    @Override
    public void execute(String... args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {
        if (args.length == 0){
            throw new CommandParamsException(0, EXPECTED_ARGUMENTS_COUNT);
        }
        if (FileValidator.fileCheck(args[PATH_INDEX])){
            filePath = args[PATH_INDEX];
            if (recursionCheck(filePath)){
                listener = new FileListener(filePath, invoker);
                listenersQueue.add(listener);
                listener.start();
                pathChain.remove(filePath);
            }
            else {
                for (IListener listener : listenersQueue){
                    listener.stop();
                }
                recursionFlag = true;
                throw new RecursionException();
            }
        }
        if (recursionFlag){
            if(this.listener.equals(listenersQueue.getFirst())){
                listenersQueue = new LinkedList<>();
                recursionFlag = false;
            }
        }
        if(listener.equals(listenersQueue.getFirst())){
            listenersQueue = new LinkedList<>();
            invoker.getPrinter().print("Script was successfully executed!");
        }
    }

    private boolean recursionCheck(String filePath){
        if (pathChain.contains(filePath)){
            return false;
        }
        pathChain.add(filePath);
        return true;
    }
}
