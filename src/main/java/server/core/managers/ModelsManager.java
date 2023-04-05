package server.core.managers;

import server.core.validators.ModelsValidator;
import shared.commands.enums.DataField;
import server.core.comparators.ModelsDefaultComparator;
import shared.core.models.*;
import shared.interfaces.IPrinter;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Contains tools to manage your collection.
 */
public class ModelsManager {
    private static final Logger logger = Logger.getLogger(ModelsManager.class.getName());
    private ArrayList<Long> usedIDs;
    private ArrayDeque<MusicBand> models;
    private String creationDate;


    public ModelsManager(ArrayDeque<MusicBand> models){
        this.models = models;
        getModelsIDs();
        creationDate = ZonedDateTime.now().toLocalDate().toString();
    }

    /**
     * Creates new model with random ID.
     * @param data data Map for model's constructor.
     * @return new model.
     */
    public MusicBand createModel(Map<DataField, Object> data, IPrinter printer){
        printer.print("Starting object creating...");
        logger.log(Level.INFO, "Creating new model.");
        return new MusicBand(
                generateId(),
                (String)data.get(DataField.NAME),
                (Coordinates) data.get(DataField.COORDINATES),
                (int)data.get(DataField.NUMBER_OF_PARTICIPANTS),
                (MusicGenre)data.get(DataField.GENRE),
                (Person) data.get(DataField.FRONTMAN)
                );
    }

    /**
     * Creates new model with custom ID.
     * @param data data Map for model's constructor.
     * @param id Desired id for the model.
     * @return new model.
     */
    public MusicBand createModel(Map<DataField, Object> data, long id, IPrinter printer){
        printer.print("Starting object creating...");
        logger.log(Level.INFO, "Creating new model.");
        return new MusicBand(
                id,
                (String)data.get(DataField.NAME),
                (Coordinates) data.get(DataField.COORDINATES),
                (int)data.get(DataField.NUMBER_OF_PARTICIPANTS),
                (MusicGenre)data.get(DataField.GENRE),
                (Person) data.get(DataField.FRONTMAN)
        );
    }

    /**
     * Add models ArrayDeque to the collection.
     * @param queue Models collection.
     */
    public void addModels(ArrayDeque<MusicBand> queue){
        models.addAll(queue);
        queue.stream().map(MusicBand::getId).forEach(usedIDs::add);
        sort();
    }
    /**
     * Add model to the collection.
     * @param model Model object.
     */
    public boolean addModels(MusicBand model){
        if(ModelsValidator.modelCheck(new MusicBandClone(model))){
            models.add(model);
            usedIDs.add(model.getId());
            sort();
            return true;
        }
        return false;
    }

    /**
     * Get model from the collection by ID and recreate it.
     * @param id Model id.
     * @param data new model data.
     */
    public void updateModel(long id, Map<DataField, Object> data, IPrinter printer){
        logger.log(Level.INFO, "Updating model.");
        MusicBand model = findModelById(id, printer);
        model.setName((String)data.get(DataField.NAME));
        model.setCoordinates((Coordinates) data.get(DataField.COORDINATES));
        model.setNumberOfParticipants((int)data.get(DataField.NUMBER_OF_PARTICIPANTS));
        model.setGenre((MusicGenre)data.get(DataField.GENRE));
        model.setFrontMan((Person) data.get(DataField.FRONTMAN));
    }

    /**
     * Find model in the collection by id.
     * @param id model id.
     * @return object of model.
     */
    public MusicBand findModelById(Long id, IPrinter printer){
        if (models.size()==0){
            printer.print("Collection is empty!");
            return null;
        }
        MusicBand[] acceptedModels = models.stream().filter(x->x.getId() == id).toArray(MusicBand[]::new);
        if (acceptedModels.length == 0){
            printer.print("Can not find element with this id.");
            return null;
        }
        return acceptedModels[0];
    }

    /**
     * create new id for creating/loading model.
     * @return new ID.
     */
    private long generateId(){
        Random rnd = new Random();
        long id = rnd.nextLong(Long.MAX_VALUE);
        while(usedIDs.contains(id)){
            id = rnd.nextLong();
        }
        usedIDs.add(id);
        return id;
    }

    /**
     * remove all elements from the collection.
     */
    public void removeAll(IPrinter printer){
        models.stream().forEach(models::remove);
        usedIDs.clear();
    }

    /**
     * Makes the default sorting.
     */
    public void sort(){
        models = models.stream().sorted(new ModelsDefaultComparator()).collect(Collectors.toCollection(ArrayDeque<MusicBand>::new));
    }

    /**
     * Remove model from the collection by model id.
     * @param id model id.
     */
    public void removeById(long id, IPrinter printer){
        MusicBand musicBand = findModelById(id, printer);
        if (musicBand != null){
            logger.log(Level.INFO, "Removing model.");
            models.remove(musicBand);
            usedIDs.remove(id);

            printer.print(String.format("Model %s successfully removed.", id));
        }
        else printer.print("Model does not exist!");
    }


    public ArrayDeque<MusicBand> getModels() {
        return models;
    }

    public ArrayList<Long> getUsedIDs() {
        return usedIDs;
    }

    public String getCreationDate() {
        return creationDate;
    }

    private void getModelsIDs(){
        usedIDs = new ArrayList<>();
        for(MusicBand musicBand : models){
            usedIDs.add(musicBand.getId());
        }
    }
}
