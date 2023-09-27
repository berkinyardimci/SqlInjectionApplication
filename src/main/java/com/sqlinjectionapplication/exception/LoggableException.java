package com.sqlinjectionapplication.exception;

public abstract class LoggableException extends RuntimeException{

    public LoggableException(String password) {
        super(password);
    }

    public abstract String getExMessage();
}
