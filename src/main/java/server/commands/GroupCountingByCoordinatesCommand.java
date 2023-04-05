package server.commands;

import server.core.Invoker;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;
import shared.core.models.Coordinates;
import shared.core.models.MusicBand;
import java.util.HashMap;

/**
 * The class contains an implementation of the group_counting_by_coordinates command
 */

public class GroupCountingByCoordinatesCommand extends Command{

    public GroupCountingByCoordinatesCommand(Invoker invoker) {
        super(invoker);
    }

    @Override
    public String execute(Object args) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {
        HashMap<Coordinates, Integer> counter = new HashMap<>();
        if (invoker.getModelsManager().getModels().isEmpty()){
            return "Collection is empty!";
        }

        Coordinates[] uniqueCoordinates = invoker.getModelsManager().getModels().stream().map(MusicBand::getCoordinates).distinct().toArray(Coordinates[]::new);
        for(Coordinates coordinates : uniqueCoordinates){
            long count = invoker.getModelsManager().getModels().stream().filter(x->x.getCoordinates().equals(coordinates)).count();
            invoker.getPrinter().print(String.format("Group of Coordinates: \n%s\nCount of elements: %s",
                    coordinates, count));
        }

        return "Counting ended...";
    }

    @Override
    public String getCommandInfo() {
        return "Command \"group_counting_by_coordinates\": This command allows you to group the items in the collection by coordinates field, displaying the number of items in each group.";
    }
}
