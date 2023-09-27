package com.sqlinjectionapplication.exception;


import com.sqlinjectionapplication.utils.constant.ErrorMessage;

public class UserNotFoundException extends RuntimeException{

    private String username;

    public UserNotFoundException(String username){
        this.username = username;
    }

    public String getExMessage(){
        return this.username + " " + ErrorMessage.USERNAME_NOT_FOUND;
    }
}
