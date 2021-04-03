package com.group.pdc_assignment_rpg.exceptions;

/**
 * Exception thrown when loading an invalid game map.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
@SuppressWarnings("serial")
public class InvalidMapException extends Exception {
    public InvalidMapException() {
        super("File is not a valid game map.");
    }
    
    public InvalidMapException(String message) {
        super(message);
    }
}
