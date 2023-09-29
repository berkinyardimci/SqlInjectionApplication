package com.sqlinjectionapplication.utils.aop;

import com.sqlinjectionapplication.exception.LoggableException;
import com.sqlinjectionapplication.exception.PasswordNotValidException;
import com.sqlinjectionapplication.exception.UserNameNotValidException;
import com.sqlinjectionapplication.interceptor.RequestInterceptor;
import com.sqlinjectionapplication.rabbitMq.model.LogModel;
import com.sqlinjectionapplication.rabbitMq.producer.LogProducer;
import jakarta.servlet.http.HttpServletRequest;
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
    HttpServletRequest currentRequest = RequestInterceptor.getCurrentRequest();

    if (ex instanceof LoggableException) {
        LoggableException loggableException = (LoggableException) ex;
        String query = getQuery();
        String logMessage = loggableException.getExMessage();

        //logger.error(logMessage + " : {}", query);

        LogModel logModel = LogModel.builder()
                .query(String.format("%s : {%s}", logMessage, query))
                .httpMethod(currentRequest.getMethod())
                .httpUri(currentRequest.getRequestURI())
                .httpUrl(currentRequest.getRequestURL().toString())
                .build();
        this.logProducer.sendLog(logModel);
    }
}
}
