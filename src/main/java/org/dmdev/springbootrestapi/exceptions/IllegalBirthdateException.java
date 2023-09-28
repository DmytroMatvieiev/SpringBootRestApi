package org.dmdev.springbootrestapi.exceptions;

public class IllegalBirthdateException extends RuntimeException{
    public IllegalBirthdateException(String message) {
        super(message);
    }
}
