package com.sqlinjectionapplication.exception;


import com.sqlinjectionapplication.utils.constant.ErrorMessage;

public class UserNameNotValidException extends LoggableException{

    private String username;

    public UserNameNotValidException(String username) {
        super(username);
        this.username = username;
    }

    @Override
    public String getExMessage() {
        return this.username + " " + ErrorMessage.USERNAME_NOT_VALID;
    }

}
