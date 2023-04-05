package client.core.validators;

import shared.core.exceptions.InvalidArgumentsLimitsException;
import client.core.interfaces.IListener;
import shared.interfaces.IPrinter;
import shared.interfaces.Validator;

import java.lang.reflect.InvocationTargetException;


/**
 * Contains the validation logic for all user input.
 */
public class CommandsDataValidator {
    private static final int VALIDATOR_INDEX = 0;
    /**
     * Checks the string to see if it is empty or consists only of spaces.
     * @param name line to validate.
     * @param listener client.commands listener.
     * @param printer current output printer.
     * @param allowedNull can be null?
     * @return validated string.
     */
    public static String nameCheck(String name, final IListener listener, final IPrinter printer, boolean allowedNull){
        if(name.isBlank() && !allowedNull){
            printer.print("The string entered was in the wrong format. \n" +
                    "Requirements: \n" +
                    "-The string must not be empty\n" +
                    "-The string must not be null\n" +
                    "Please, try again: ");
            return nameCheck(listener.nextLine(),listener, printer, allowedNull);
        }
        else{
            return name;
        }
    }

    /**
     * Prorates all numbers depending on the expected class.
     * @param line line to validate.
     * @param listener client.commands listner.
     * @param printer current output printer.
     * @param clazz expected number type.
     * @param allowedNull can be null?
     * @param args arguments for reflection method
     * @return validated number in needed type.
     */
    public static Number numbersCheck(String line, final IListener listener, final IPrinter printer, Class clazz, boolean allowedNull, Validator ... args){
        try{
            if (line.isBlank() && allowedNull){
                return null;
            }
            if (clazz == Float.class){
                line = line.replace(",", ".");
                if (args.length==0 || args[VALIDATOR_INDEX].validate(Float.parseFloat(line))){
                    return Float.parseFloat(line);
                }
                throw new InvalidArgumentsLimitsException("Check values limits.");
            }
            if (clazz == Integer.class){
                if (args.length==0 || args[VALIDATOR_INDEX].validate(Integer.parseInt(line))){
                    return Integer.parseInt(line);
                }
                throw new InvalidArgumentsLimitsException("Check values limits.");
            }
            if(clazz == Double.class){
                line = line.replace(",", ".");
                if (args.length==0 || args[VALIDATOR_INDEX].validate(Double.parseDouble(line))){
                    return Double.parseDouble(line);
                }
                throw new InvalidArgumentsLimitsException("Check values limits.");
            }
            if(clazz == Long.class){
                if (args.length==0 || args[VALIDATOR_INDEX].validate(Long.parseLong(line))){
                    return Long.parseLong(line);
                }
                throw new InvalidArgumentsLimitsException("Check values limits.");
            }
        }
        catch (InvalidArgumentsLimitsException | NumberFormatException ex){
            printer.print("The string entered was in the wrong format. \n" +
                    "Requirements: \n" +
                    "-The string must have the format: " + clazz.getName() + "\n" +
                    "-Check values limits.\n"+
                    "-Use command \"help\" for more information."+
                    "\nPlease, try enter value again: ");
            return numbersCheck(listener.nextLine(), listener, printer, clazz,allowedNull, args);
        }
        return 0;
    }

    /**
     * Contains the validation logic for enums.
     * @param line user input.
     * @param listener client.commands listener.
     * @param printer current output printer.
     * @param enumClass enum class.
     * @param allowedNull can be null?
     * @return validated enum value.
     * @param <E>
     */
    public static  <E extends Enum> E enumCheck(String line, final IListener listener, final IPrinter printer, Class enumClass, boolean allowedNull){
        try{
            if (line.isBlank()  && allowedNull){
                return null;
            }
            try {
                int index = Integer.parseInt(line);
                Object value = ((Object[]) enumClass.getMethod("values", null).invoke(null))[index-1];
                return (E)value;
            }
            catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception){
                throw new RuntimeException();
            }
            catch (NumberFormatException  ex){}
            return (E)E.valueOf(enumClass,line.toUpperCase());
        }
        catch (IndexOutOfBoundsException | IllegalArgumentException ex){
            printer.print("The value entered is not in the list, try again: ");
            return enumCheck(listener.nextLine(), listener, printer, enumClass, allowedNull);
        }
    }

}
