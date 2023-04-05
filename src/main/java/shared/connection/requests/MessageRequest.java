package shared.connection.requests;

import shared.connection.interfaces.IRequest;

import java.io.Serializable;

public class MessageRequest implements IRequest, Serializable {
    private Object data;
    public MessageRequest(Object data){
        this.data = data;
    }
    @Override
    public Object getData() {
        return data;
    }
}
