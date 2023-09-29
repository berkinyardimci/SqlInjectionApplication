package com.sqlinjectionapplication.exception;

import com.sqlinjectionapplication.data.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UserNameNotValidException.class)
    public ResponseEntity<GenericResponse> handleUsernameNotValidException(UserNameNotValidException ex){
        String message = ex.getExMessage();
        return getGenericResponse(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordNotValidException.class)
    public ResponseEntity<GenericResponse> handlePasswordNotValidException(PasswordNotValidException ex){
        String message = ex.getExMessage();
        return getGenericResponse(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<GenericResponse> handledUserNotFoundException(UserNotFoundException ex){
        String message = ex.getExMessage();
        return getGenericResponse(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<GenericResponse> handledUserNotFoundException(UserAlreadyExistException ex){
        String message = ex.getExMessage();
        return getGenericResponse(message, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<GenericResponse> getGenericResponse(String ex, HttpStatus httpStatus){
        GenericResponse response =new GenericResponse(
                httpStatus,
                httpStatus.value(),
                ex,
                LocalDateTime.now()
        );
        return ResponseEntity.status(httpStatus).body(response);
    }
}
