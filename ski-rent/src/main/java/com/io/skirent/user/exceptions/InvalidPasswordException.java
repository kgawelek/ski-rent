package com.io.skirent.user.exceptions;

public class InvalidPasswordException extends Exception {

    public InvalidPasswordException(String errorMessage) {
        super(errorMessage);
    }

}
