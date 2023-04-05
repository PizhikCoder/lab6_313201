package client.connection;

import client.connection.interfaces.IClientConnection;
import client.connection.interfaces.IMessageSender;
import shared.connection.interfaces.IRequest;
import shared.interfaces.IPrinter;

import java.io.*;
import java.net.Socket;

public class TCPSender implements IMessageSender {

    private Socket socket;

    private IPrinter printer;

    public TCPSender(Socket socket, IPrinter printer){
        this.socket = socket;
        this.printer = printer;
    }


    /**
     * Sends a request to the server
     * @param data
     */
    @Override
    public void send(IRequest data) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStreamWriter = new ObjectOutputStream(byteArrayOutputStream);
            outputStreamWriter.writeObject(data);
            OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();
        }
        catch (IOException exception){
            printer.print("Can not serialize request!");
        }
    }
}
