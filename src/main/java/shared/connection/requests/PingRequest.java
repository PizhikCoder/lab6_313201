package shared.connection.requests;

import shared.connection.interfaces.IRequest;

import java.io.Serializable;

public class PingRequest implements Serializable, IRequest {
    @Override
    public Object getData() {
        return null;
    }
}
