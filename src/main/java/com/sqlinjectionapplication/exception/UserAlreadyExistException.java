package com.sqlinjectionapplication.exception;


import com.sqlinjectionapplication.utils.constant.ErrorMessage;

public class UserAlreadyExistException extends RuntimeException{

    private String username;

    public UserAlreadyExistException(String username){
        this.username = username;
    }

    public String getExMessage(){
        return this.username + " " + ErrorMessage.USERNAME_ALREADY_EXIST;

    }
}