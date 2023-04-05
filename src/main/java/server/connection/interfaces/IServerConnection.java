package server.connection.interfaces;

import shared.connection.interfaces.IRequest;

/**
 * Server connection
 */
public interface IServerConnection {
    void send(IRequest data);
    void sendToAll(IRequest data);
}
