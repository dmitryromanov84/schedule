package com.hornsnhuffs.schedule.web;

public class UnableToProcessException extends Exception {

    public UnableToProcessException(String message) {
        super(message);
    }

    public UnableToProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
