package client.core.listeners;

import client.core.Invoker;
import client.core.managers.CommandsManager;
import client.core.interfaces.IListener;
import shared.interfaces.IPrinter;

import java.util.Scanner;

/**
 * Contains tools to listen for console input.
 */
public class CLIListener implements IListener {
    private volatile Boolean isWorking = false;
    private CommandsManager commandsManager;
    private final IPrinter printer;
    private final Invoker invoker;
    private Scanner scanner;

    public CLIListener(Invoker invoker){
        this.invoker = invoker;
        printer = invoker.getPrinter();
    }


    /**
     * Starts listening to client.commands from the console using Scanner
     */
    @Override
    public void start() {
        scanner = new Scanner(System.in);
        isWorking = true;
        printer.print("CLIListener work is started.");
        commandsManager = new CommandsManager(invoker);
        while(isWorking){
            String line = nextLine();
            commandsManager.parseLine(line);
        }
    }

    /**
     * Starts listening to client.commands from the console.
     */
    public void stop(){
        isWorking = false;
    }

    /**
     * Returns a string from the console using Scanner.
     * @return console input.
     */
    @Override
    public String nextLine() {
        System.out.printf(">");
        return scanner.nextLine();
    }

    public Scanner getScanner() {
        return scanner;
    }

    public Boolean getWorking(){
        return isWorking;
    }

    public CommandsManager getCommandsManager() {
        return commandsManager;
    }
}
