package shared.commands.commandsdtos;

import java.io.Serializable;

public class CommandDTO implements Serializable {
    private String name;
    public CommandDTO(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
