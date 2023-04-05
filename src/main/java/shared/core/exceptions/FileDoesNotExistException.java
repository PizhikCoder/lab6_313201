package shared.core.exceptions;

/**
 * Discarded if the file does not exist.
 */
public class FileDoesNotExistException extends Exception{
    private final static String exception_log = "File does not exist!";
    public FileDoesNotExistException(){
        super(exception_log);
    }
}
