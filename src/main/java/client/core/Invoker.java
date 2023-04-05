package client.core;

import client.commands.Command;
import client.connection.interfaces.IClientConnection;
import shared.core.exceptions.*;
import client.core.listeners.CLIListener;
import client.core.interfaces.IListener;
import shared.interfaces.IPrinter;

import java.awt.*;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * client.Main class. Contains all the logic for linking all classes of the programme.
 */
public class Invoker {

    private IPrinter printer;

    private IListener listener;

    private IClientConnection connection;

    private PipedInputStream pipedInputStream;

    public Invoker(IPrinter printer, PipedOutputStream pipedOutputStream, IClientConnection connection) throws IOException, ConnectionException {
        this.printer = printer;
        this.listener = new CLIListener(this);
        this.pipedInputStream = new PipedInputStream();
        this.pipedInputStream.connect(pipedOutputStream);
        this.connection = connection;
        printer.print("Creating connection...");
        connection.connect(this);
    }


    /**
     * Start client.commands listening.
     */
    public void startListening(){
        printer.print("Starting listening...");
        listener.start();
    }

    /**
     * Invoke command logic.
     * @param command command's object.
     * @param arguments command's arguments.
     */
    public void invokeCommand(Command command, String ... arguments){
        try{
            if (command != null){
                if (!connection.isConnected())
                command.execute(arguments);
            }
        }
        catch (RecursionException exception){
            printer.print("Recursion exception.");
            printer.print(exception.getMessage());
        }

        catch (CommandParamsException exception){
            printer.print("Params exception.");
            printer.print(exception.getMessage());
        }
        catch (FileDoesNotExistException exception){
            printer.print("File exception.");
            printer.print(exception.getMessage());
        }
        catch (ArgumentLimitsException exception){
            printer.print("Argument limits exception.");
            printer.print(exception.getMessage());
        }
        catch (FileAccessException exception){
            printer.print("Command exception.");
            printer.print(exception.getMessage());
        }
    }

    public IPrinter getPrinter() {
        return printer;
    }

    public IListener getListener() {
        return listener;
    }

    public IClientConnection getConnection(){
        return connection;
    }

    public PipedInputStream getPipedInputStream(){
        return pipedInputStream;
    }
}
