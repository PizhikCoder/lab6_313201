package server;

import server.connection.ConnectionHandler;
import server.core.ClonesParser;
import server.core.Invoker;
import server.core.datahandlers.YAMLHandler;
import shared.connection.requests.MessageRequest;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import server.core.managers.ModelsManager;
import server.core.validators.FileValidator;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class Main {
    public static Logger logger;

    private static Timer timer;

    /**
     * The period of automatic saving of the collection.
     */
    private static final int TIMER_DELAY = 60000;

    private static final int PORT_INDEX = 0;

    /**
     * Localhost address
     */
    private static final String HOST_NAME = "127.0.0.1";

    private static final int DEFAULT_PORT = 2222;



    public static void main(String ... args){
        configureLogger();
        logger.log(Level.INFO, "Server is running.");

        int port = checkArgs(args);

        ModelsManager modelsManager = new ModelsManager(new ArrayDeque<>());
        YAMLHandler yamlHandler = new YAMLHandler("data.yaml", modelsManager);
        ConnectionHandler connectionHandler = new ConnectionHandler(HOST_NAME , port, yamlHandler, yamlHandler, modelsManager);

        Invoker invoker = connectionHandler.getInvoker();
        try{
            if (FileValidator.fileCheck("data.yaml")){
                invoker.loadData();
            }
        }
        catch (FileAccessException | FileDoesNotExistException exception){

            logger.log(Level.WARNING, "Exception: ", exception);
            logger.log(Level.INFO, "Creating own data file...");
            try{
                yamlHandler.setFilePath("data.yaml");
                File file = new File("data.yaml");
                if(file.createNewFile()){
                    logger.log(Level.INFO, "data.yaml created.");
                }
                else {
                    throw new IOException();
                }
            }
            catch (IOException IOex){
                logger.log(Level.WARNING, "Can not create new file!", IOex);
                return;
            }
        }

        initializeTimer(invoker);
        timer.start();
        connectionHandler.waitConnection();
    }

    /**
     * Initializes a timer for synchronous saves.
     * @param invoker
     */
    private static void initializeTimer(Invoker invoker){
        timer = new Timer(TIMER_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (invoker.getDataSaver().save(ClonesParser.toClones(ClonesParser.dequeToArray(invoker.getModelsManager().getModels())))){
                    invoker.getConnection().sendToAll(new MessageRequest("[SERVER]: Models are automatically saved!"));
                    logger.log(Level.INFO, "Models are automatically saved");
                }
            }
        });
    }

    /**
     * Loads the logger configuration
     */
    private static void configureLogger(){
        try{
            new File("logs").mkdir();
        }
        catch (SecurityException exception){
            System.err.println("Can not create logs directory.");
        }
        try(FileInputStream fileInputStream = new FileInputStream("log.config")) {
            LogManager.getLogManager().readConfiguration(fileInputStream);
            logger = Logger.getLogger(Main.class.getName());
        }
        catch (IOException exception){
            System.err.println(exception.getMessage());
            System.out.println("Can not load logger's configuration");
            System.exit(1);
        }
    }

    private static int checkArgs(String ... args){
        int port = 0;
        try{
            if (args.length == 0){
                logger.log(Level.INFO,"Expected 1 argument, received 0");
                System.exit(1);
            }
            port = Integer.parseInt(args[PORT_INDEX]);
            if(port<=1023){
                logger.log(Level.INFO, "Can not start server on this port!");
                System.exit(1);
            }
        }
        catch (NumberFormatException exception){
            logger.log(Level.WARNING,"Port in the wrong format. Expected Integer.");
            System.exit(1);
        }
        return port;
    }
}
