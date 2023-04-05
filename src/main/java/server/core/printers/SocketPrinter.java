package server.core.printers;

import server.connection.interfaces.IServerConnection;
import shared.connection.requests.MessageRequest;
import shared.interfaces.IPrinter;

/**
 * Sends a text message.
 */
public class SocketPrinter implements IPrinter {

    private IServerConnection clientConnection;

    public SocketPrinter(IServerConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    @Override
    public void print(String data) {
        clientConnection.send(new MessageRequest("[SERVER]: "+data));
    }
}
