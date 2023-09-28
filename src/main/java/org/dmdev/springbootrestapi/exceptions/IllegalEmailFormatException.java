package org.dmdev.springbootrestapi.exceptions;

public class IllegalEmailFormatException extends RuntimeException{
    public IllegalEmailFormatException(String message) {
        super(message);
    }
}
