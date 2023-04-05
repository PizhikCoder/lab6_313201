package server.core.datahandlers;

import org.yaml.snakeyaml.constructor.ConstructorException;
import server.core.managers.ModelsManager;
import shared.interfaces.IDataLoader;
import shared.interfaces.IDataSaver;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains logic for work with source YAML file.
 */
public class YAMLHandler implements IDataSaver, IDataLoader {
    private final Logger logger = Logger.getLogger(YAMLHandler.class.getName());
    private String filePath;

    private boolean isDataLoading = false;

    private ModelsManager modelsManager;

    public YAMLHandler(String path, ModelsManager modelsManager){
        filePath = path;
        this.modelsManager = modelsManager;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public <T> T load(Class<T> clazz) {
        Yaml yaml = new Yaml();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            return yaml.loadAs(reader, clazz);
        }
        catch (ConstructorException ex){
            logger.log(Level.WARNING, "Exception log!\nCan not parse data from file.\nCheck file path and file validity...", ex);
            return null;
        }
        catch (IOException ex){
            logger.log(Level.WARNING, "Exception while working with data file.");
            return null;
        }
    }


    @Override
    public <T> boolean save(T data) {
        Yaml yaml = new Yaml();

        try(OutputStreamWriter streamWriter = new OutputStreamWriter(new FileOutputStream(filePath))){
            yaml.dump(data, streamWriter);
            return true;
        }
        catch (IOException ex){
            logger.log(Level.WARNING, "Exception file was writing.\nCheck file path and file validity...", ex);
            return  false;
        }
    }

    @Override
    public boolean getIsDataLoading() {
        return isDataLoading;
    }

    @Override
    public void setIsDataLoading(boolean status) {
        this.isDataLoading = status;
    }
}
