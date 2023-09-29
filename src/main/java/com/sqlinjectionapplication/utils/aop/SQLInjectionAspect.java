package com.sqlinjectionapplication.utils.aop;

import com.sqlinjectionapplication.data.request.CreateUserRequest;
import com.sqlinjectionapplication.data.request.LoginUserRequest;
import com.sqlinjectionapplication.exception.PasswordNotValidException;
import com.sqlinjectionapplication.exception.UserNameNotValidException;
import com.sqlinjectionapplication.utils.constant.ErrorMessage;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SQLInjectionAspect {

    private static final String SUITABLE_CHARACTERS_FOR_USERNAME_REGEX = "^[a-zA-Z0-9]{1,10}$";
    private static final String SUITABLE_CHARACTERS_FOR_PASSWORD_REGEX = "^(?=.*[A-ZA-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{5,10}";


    @After("execution(* com.sqlinjectionapplication.service.UserService.createUser(..)) && args(request)")
    public void beforeLogin(CreateUserRequest request) {
        if (!isValidUsernameRegex(request.username())) {
            throw new UserNameNotValidException(request.username());
        } else if (!isValidPasswordRegex(request.password())) {
            throw new PasswordNotValidException(request.password());
        }
    }
    @After("execution(* com.sqlinjectionapplication.service.UserService.login(..)) && args(request)")
    public void beforeLogin(LoginUserRequest request) {
        if (!isValidUsernameRegex(request.username())) {
            throw new UserNameNotValidException(request.username());
        } else if (!isValidPasswordRegex(request.password())) {
            throw new PasswordNotValidException(request.password());
        }
    }

    private boolean isValidUsernameRegex(String username) {
        return username.matches(SUITABLE_CHARACTERS_FOR_USERNAME_REGEX);
    }

    private boolean isValidPasswordRegex(String username) {
        return username.matches(SUITABLE_CHARACTERS_FOR_PASSWORD_REGEX);
    }


}
