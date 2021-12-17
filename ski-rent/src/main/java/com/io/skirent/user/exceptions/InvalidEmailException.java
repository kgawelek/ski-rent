package com.io.skirent.user.exceptions;

public class InvalidEmailException extends Exception {

    public InvalidEmailException(String errorMessage) {
        super(errorMessage);
    }

}
