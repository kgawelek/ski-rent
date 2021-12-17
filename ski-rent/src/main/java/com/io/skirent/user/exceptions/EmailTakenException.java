package com.io.skirent.user.exceptions;

public class EmailTakenException extends Exception {

    public EmailTakenException(String errorMessage) {
        super(errorMessage);
    }

}
