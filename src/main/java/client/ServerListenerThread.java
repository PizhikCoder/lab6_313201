package client;

import client.connection.interfaces.IClientConnection;
import shared.connection.interfaces.IRequest;
import shared.connection.requests.CommandRequest;
import shared.connection.requests.MessageRequest;
import shared.connection.requests.ValidationRequest;
import shared.core.exceptions.ConnectionException;
import shared.interfaces.IPrinter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PipedOutputStream;
import java.util.NoSuchElementException;

/**
 * Stream class for background server wiretapping.
 */
public class ServerListenerThread extends Thread{

    private PipedOutputStream pipedOutputStream;

    private IClientConnection connection;

    private IPrinter printer;

    /**
     * A stream for transferring data to the main thread.
     * @param stream
     * @param connection
     * @param printer
     */
    public ServerListenerThread(PipedOutputStream stream, IClientConnection connection, IPrinter printer){
        pipedOutputStream = stream;
        this.connection = connection;
        this.printer = printer;
    }


    public void run(){
        startListening();
    }

    private void startListening(){
        while(!isInterrupted()){
            try{
                IRequest response = connection.getResponse();
                if (response instanceof MessageRequest){
                    handleMessageRequest(response);
                }
                else if(response instanceof ValidationRequest){
                    if (response.getData() == null) continue;
                    handleValidationRequest(response);
                }
                else if(response instanceof CommandRequest){
                    handleCommandRequest(response);
                }
            }
            catch (ConnectionException exception){
                return;
            }
        }
    }

    private void handleCommandRequest(IRequest response){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            pipedOutputStream.write(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            printer.print("Can not write command request to the piped stream!");
        }
    }

    private void handleValidationRequest(IRequest response){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            pipedOutputStream.write(byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            printer.print("Can not write validation request to the piped stream!");
        }
    }

    private void handleMessageRequest(IRequest response){
        printer.print((String)response.getData());
    }
}
