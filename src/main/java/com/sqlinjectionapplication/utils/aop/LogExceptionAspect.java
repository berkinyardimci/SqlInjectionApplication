package com.sqlinjectionapplication.utils.aop;

import com.sqlinjectionapplication.exception.PasswordNotValidException;
import com.sqlinjectionapplication.exception.UserNameNotValidException;
import com.sqlinjectionapplication.rabbitMq.model.LogModel;
import com.sqlinjectionapplication.rabbitMq.producer.LogProducer;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogExceptionAspect {

    private final LogProducer logProducer;

    private final Logger logger = LoggerFactory.getLogger(LogExceptionAspect.class);

    private ThreadLocal<String> queryThreadLocal = new ThreadLocal<>();

    public LogExceptionAspect(LogProducer logProducer) {
        this.logProducer = logProducer;
    }

    public void setQuery(String query) {
        queryThreadLocal.set(query);
    }

    public String getQuery() {
        return queryThreadLocal.get();
    }

    public void clearQuery() {
        queryThreadLocal.remove();
    }

    @AfterThrowing(pointcut = "execution(* com.sqlinjectionapplication.service.UserService.*(..))", throwing = "ex")
    public void logError(Exception ex) {
        LogModel logModel;
        if (ex instanceof PasswordNotValidException) {
            logger.error("Danger Password query : {}", getQuery());
            logModel = LogModel.builder()
                    .query(String.format("Danger Password query : {%s}", getQuery()))
                    .build();
        } else if (ex instanceof UserNameNotValidException) {
            logger.error("Danger Username query : {}", getQuery());
            logModel = LogModel.builder()
                    .query(String.format("Danger Username query : {%s}", getQuery()))
                    .build();
        } else {
            logger.error("Danger query : {}", getQuery());
            logModel = LogModel.builder()
                    .query(String.format("Danger query : {%s}", getQuery()))
                    .build();
        }
        this.logProducer.sendLoginLog(logModel);
    }
}
