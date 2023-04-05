package server.core;

import server.commands.Command;
import server.connection.interfaces.IServerConnection;
import server.core.managers.CommandsManager;
import server.core.managers.ModelsManager;
import shared.connection.requests.CommandRequest;
import shared.core.exceptions.*;
import shared.core.models.MusicBand;
import shared.core.models.MusicBandClone;
import server.core.validators.ModelsValidator;
import shared.interfaces.IDataLoader;
import shared.interfaces.IDataSaver;
import shared.interfaces.IPrinter;
import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class. Contains all the logic for linking all classes of the programme.
 */
public class Invoker {

    private static final Logger logger = Logger.getLogger(Invoker.class.getName());

    private static boolean isDataLoading = false;

    private IPrinter printer;

    private ModelsManager modelsManager;

    private IServerConnection connection;

    private IDataSaver dataSaver;

    private IDataLoader dataLoader;

    private CommandsManager commandsManager;


    public Invoker(IPrinter printer, IDataSaver saver, IDataLoader loader, ModelsManager modelsManager, CommandsManager commandsManager){
        this.printer = printer;
        this.modelsManager = modelsManager;
        this.dataSaver = saver;
        this.dataLoader = loader;
        this.commandsManager = commandsManager;
    }

    /**
     * Invoke command logic.
     * @param command command's object.
     * @param arguments command's arguments.
     */
    public void invokeCommand(Command command, Object arguments){
        try{
            if (command != null) {
                printer.print(command.execute(arguments));
                connection.send(new CommandRequest(null, true));
            }
        }
        catch (RecursionException | FileAccessException | CommandParamsException | FileDoesNotExistException |
               ArgumentLimitsException ex){
            logger.log(Level.WARNING, "Something went wrong while working with command.", ex);
        }
    }

    /**
     * This method loads data from file
     */
    public void loadData(){
        logger.log(Level.INFO,"Data loading started...");
        isDataLoading = true;
        ArrayDeque<MusicBand> queue = new ArrayDeque<>();

        MusicBand[] arr = ClonesParser.toOrigs(ModelsValidator.modelsCheck(dataLoader.load(MusicBandClone[].class), this));
        for(MusicBand i : arr){
            queue.add(i);
        }

        isDataLoading = false;
        modelsManager.getUsedIDs().clear();
        modelsManager.addModels(queue);
        modelsManager.sort();
        logger.log(Level.INFO, "Data loading finished.");
    }


    public void setConnection(IServerConnection connection){
        this.connection = connection;
    }

    public static boolean getIsDataLoading(){
        return isDataLoading;
    }

    public IPrinter getPrinter() {
        return printer;
    }


    public ModelsManager getModelsManager() {
        return modelsManager;
    }

    public IDataSaver getDataSaver(){
        return dataSaver;
    }


    public IServerConnection getConnection() {
        return connection;
    }

    public CommandsManager getCommandsManager(){
        return commandsManager;
    }

}
