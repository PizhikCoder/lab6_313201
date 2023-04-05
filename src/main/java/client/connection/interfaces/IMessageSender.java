package client.connection.interfaces;

import shared.connection.interfaces.IRequest;
import shared.interfaces.IPrinter;

public interface IMessageSender {
    void send(IRequest data);
}
