package com.sqlinjectionapplication.rabbitMq.consumer;

import com.sqlinjectionapplication.entity.Log;
import com.sqlinjectionapplication.interceptor.RequestInterceptor;
import com.sqlinjectionapplication.rabbitMq.model.LogModel;
import com.sqlinjectionapplication.repository.LogRepository;
import com.sqlinjectionapplication.utils.constant.QueueConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class LogConsumer {

    private final LogRepository logRepository;

    public LogConsumer(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @RabbitListener(queues = {QueueConstants.LOG_QUEUE_NAME}, containerFactory = "rabbitListenerContainerFactory")
    public void receive(@Payload LogModel logModel) {
        Log log = Log.builder()
                .message(logModel.getQuery())
                .createdDate(logModel.getCreatedDate())
                .httpMethod(logModel.getHttpMethod())
                .httpUri(logModel.getHttpUri())
                .httpUrl(logModel.getHttpUrl())
                .build();

        this.logRepository.save(log);
    }

}
