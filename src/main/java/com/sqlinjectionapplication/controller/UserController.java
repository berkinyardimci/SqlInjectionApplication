package com.sqlinjectionapplication.controller;

import com.sqlinjectionapplication.data.request.CreateUserRequest;
import com.sqlinjectionapplication.data.request.LoginUserRequest;
import com.sqlinjectionapplication.data.response.CreateUserResponse;
import com.sqlinjectionapplication.data.response.GenericResponse;
import com.sqlinjectionapplication.data.response.LoginUserResponse;
import com.sqlinjectionapplication.service.UserService;
import com.sqlinjectionapplication.utils.constant.SuccessMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/v1/user", produces = "application/json", consumes = "application/json")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public GenericResponse<LoginUserResponse> findByNativeUser(@RequestBody LoginUserRequest request) {
        LoginUserResponse response = userService.login(request);
        return new GenericResponse<>(
                HttpStatus.OK,
                HttpStatus.CREATED.value(),
                SuccessMessage.SUCCESS_LOGIN,
                LocalDateTime.now(),
                response
        );
    }

    @PostMapping("/createUser")
    public GenericResponse<CreateUserResponse> createUser(@RequestBody CreateUserRequest request){
        CreateUserResponse response = userService.createUser(request);
        return new GenericResponse<>(
                HttpStatus.CREATED,
                HttpStatus.CREATED.value(),
                SuccessMessage.SUCCESS_CREATE_USER,
                LocalDateTime.now(),
                response
        );
    }


    @PostMapping("/getUser")
    public GenericResponse<LoginUserResponse> getUser(@RequestBody LoginUserRequest request) {
        LoginUserResponse response = userService.queryWithSqlInjection(request);
        return new GenericResponse<>(
                HttpStatus.OK,
                HttpStatus.CREATED.value(),
                SuccessMessage.SUCCESS_LOGIN,
                LocalDateTime.now(),
                response
        );
    }


}
