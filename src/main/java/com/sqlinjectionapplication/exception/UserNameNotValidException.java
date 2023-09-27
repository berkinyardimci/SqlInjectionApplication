package com.sqlinjectionapplication.exception;


import com.sqlinjectionapplication.utils.constant.ErrorMessage;

public class UserNameNotValidException extends LoggableException{

    private String password;

    public UserNameNotValidException(String password) {
        super(password);
        this.password = password;
    }

    @Override
    public String getExMessage() {
        return this.password + " " + ErrorMessage.PASSWORD_NOT_VALID;
    }

}
