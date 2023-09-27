package com.sqlinjectionapplication.exception;


import com.sqlinjectionapplication.utils.constant.ErrorMessage;

public class PasswordNotValidException extends RuntimeException{

    private String password;

    public PasswordNotValidException(String password){
        super(password);
        this.password = password;
    }

    public String getExMessage(){
        return this.password + " " + ErrorMessage.PASSWORD_NOT_VALID;
    }

}
