package com.sqlinjectionapplication.rabbitMq.producer;


import com.sqlinjectionapplication.rabbitMq.model.LogModel;
import com.sqlinjectionapplication.utils.constant.QueueConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogProducer {

    private final RabbitTemplate rabbitTemplate;

    public LogProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendLoginLog(LogModel model){
        rabbitTemplate.convertAndSend(QueueConstants.LOG_QUEUE_NAME, model);
    }
}
