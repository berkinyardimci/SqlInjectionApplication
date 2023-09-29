package com.sqlinjectionapplication.service;


import com.sqlinjectionapplication.data.request.CreateUserRequest;
import com.sqlinjectionapplication.data.request.LoginUserRequest;
import com.sqlinjectionapplication.data.response.CreateUserResponse;
import com.sqlinjectionapplication.data.response.LoginUserResponse;
import com.sqlinjectionapplication.entity.User;
import com.sqlinjectionapplication.exception.UserAlreadyExistException;
import com.sqlinjectionapplication.exception.UserNotFoundException;
import com.sqlinjectionapplication.repository.UserRepository;
import com.sqlinjectionapplication.utils.aop.LogExceptionAspect;
import com.sqlinjectionapplication.utils.constant.ConstantQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;
    private final UserRepository userRepository;

    private final LogExceptionAspect handleExceptionAspect;

    public UserService(UserRepository userRepository, LogExceptionAspect handleExceptionAspect) {
        this.userRepository = userRepository;
        this.handleExceptionAspect = handleExceptionAspect;
    }

    public LoginUserResponse login(LoginUserRequest request) {
        Optional<User> optionalUser = findByUserNameAndPassword(request.username(),request.password());
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            return new LoginUserResponse(user.getId(),user.getUsername());
        }else {
            throw new UserNotFoundException(request.username());
        }
    }

    public CreateUserResponse createUser(CreateUserRequest request) {
        Optional<User> optionalUser = findByUserNameAndPassword(request.username(), request.password());
        if (optionalUser.isEmpty()) {
            User user = User.builder().
                    username(request.username()).
                    password(request.password())
                    .build();
            userRepository.save(user);
            return new CreateUserResponse(user.getId(), user.getUsername());
        }
        throw new UserAlreadyExistException(request.username());
    }

    private Optional<User> findByUserNameAndPassword(String username, String password) {
        String sqlString = generateSqlQueryString(ConstantQuery.FIND_USER_BY_USERNAME_PASSWORD_SECURE, username, password);
        handleExceptionAspect.setQuery(sqlString);

        String[] params = {username, password};
        Query query = entityManager.createNativeQuery(ConstantQuery.FIND_USER_BY_USERNAME_PASSWORD_SECURE);

        int paramIndex = 1;
        for (Object param : params) {
            query.setParameter(paramIndex++, param);
        }
        List<Object[]> result = query.getResultList();
        if (!(Objects.isNull(result) || result.isEmpty())) {
            Object[] row = result.get(0);
            Long id = (Long) row[0];
            String usernames = (String) row[1];

            User user = User.builder()
                    .id(id)
                    .username(usernames)
                    .build();
            return Optional.of(user);
        }
        return Optional.empty();
    }


    private String generateSqlQueryString(String query, Object... params) {
        if (params.length == 0) {
            return query;
        }
        String queryString = query;
        for (Object param : params) {
            String paramValue = param != null ? param.toString() : "null";
            queryString = queryString.replaceFirst("\\?", "'" + paramValue + "'");
        }
        return queryString;
    }

    public LoginUserResponse queryWithSqlInjection(LoginUserRequest request) {
        String[] params = {request.username(), request.password()};
        String formattedQuery = String.format(ConstantQuery.FIND_USER_BY_USERNAME_PASSWORD, params);
        //handleExceptionAspect.setQuery(formattedQuery);
        Query nativeQuery = entityManager.createNativeQuery(formattedQuery);
        List<Object[]> result = nativeQuery.getResultList();
        if (Objects.isNull(result) || result.isEmpty()) {
            throw new UserNotFoundException(request.username());
        }
        return new LoginUserResponse((Long) result.get(0)[0], (String) result.get(0)[1]);
    }
}
