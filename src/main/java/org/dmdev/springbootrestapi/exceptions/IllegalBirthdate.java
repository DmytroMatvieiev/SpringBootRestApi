package org.dmdev.springbootrestapi.exceptions;

public class IllegalBirthdate extends RuntimeException{
    public IllegalBirthdate(String message) {
        super(message);
    }
}
