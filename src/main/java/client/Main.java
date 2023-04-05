package client;

import client.connection.ClientConnection;
import client.core.Invoker;
import client.core.printers.CLIPrinter;
import shared.core.exceptions.ConnectionException;
import shared.interfaces.IPrinter;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.logging.Level;

public class Main {
    private static final int DEFAULT_PORT = 2222;

    private static final String HOST_NAME = "127.0.0.1";

    private static final int PORT_INDEX = 0;

    public static void main(String ... args){
        int port;
        try{
            if (args.length == 0){
                System.err.println("Expected 1 argument, received 0");
                return;
            }
            port = Integer.parseInt(args[PORT_INDEX]);
            if(port<=1023){
                System.err.println("Can not start client on this port!");
                return;
            }
        }
        catch (NumberFormatException exception){
            System.err.println("Port in the wrong format. Expected Integer.");
            return;
        }
        ClientConnection connection = new ClientConnection(HOST_NAME, port);
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        IPrinter printer = new CLIPrinter();
        ServerListenerThread serverListenerThread = new ServerListenerThread(pipedOutputStream, connection, printer);
        try{
            Invoker invoker = new Invoker(printer, pipedOutputStream, connection);
            serverListenerThread.setDaemon(true);
            serverListenerThread.start();
            invoker.startListening();
        }
        catch (IOException exception){
            printer.print("Can not create stream between threads!");
        }
        catch (ConnectionException exception){
            printer.print(exception.getMessage());
        }
        catch (NoSuchElementException ex){
        }
        catch (Exception exception){
            printer.print("Fatal error!");
        }
    }
}
