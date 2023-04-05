package client.connection.interfaces;

import client.core.Invoker;
import shared.connection.interfaces.IRequest;
import shared.core.exceptions.ConnectionException;

import java.net.ConnectException;

/**
 * Client connection.
 */
public interface IClientConnection {
    boolean connect(Invoker invoker) throws ConnectException, ConnectionException;

    IRequest getResponse() throws ConnectionException;

    IMessageSender getSender();

    boolean isConnected();
}
