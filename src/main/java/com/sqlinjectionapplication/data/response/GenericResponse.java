package com.sqlinjectionapplication.data.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse <T>{

    private HttpStatus httpStatus;
    private Integer statusCode;
    private String message;
    private LocalDateTime localTime;
    private T data;
    //private Map<Object,Object> errorInfoMap;


    public GenericResponse(HttpStatus httpStatus, Integer statusCode, String message, LocalDateTime localTime, T data) {
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
        this.message = message;
        this.localTime = localTime;
        this.data = data;
    }

    public GenericResponse(HttpStatus httpStatus, Integer statusCode, String message, LocalDateTime localTime) {
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
        this.message = message;
        this.localTime = localTime;
    }

}
