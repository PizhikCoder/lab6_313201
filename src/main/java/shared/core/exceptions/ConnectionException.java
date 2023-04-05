package shared.core.exceptions;

public class ConnectionException extends Exception{
    public ConnectionException(){
        super("Could not connected to the server...");
    }
}
