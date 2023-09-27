package com.sqlinjectionapplication.exception;


import com.sqlinjectionapplication.utils.constant.ErrorMessage;

public class UserNameNotValidException extends RuntimeException{


    private String username;

    public UserNameNotValidException(String username){
        super(username);
        this.username = username;
    }

    public String getExMessage(){
        return this.username + " " + ErrorMessage.USERNAME_NOT_VALID;
    }

}
