package com.sqlinjectionapplication.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    private String exchange = "exchange";
    private String logBindingKey = "bindingKey";
    private String queueLog = "log_queue";
    @Bean
    DirectExchange exchangeLog(){
        return new DirectExchange(exchange);
    }
    @Bean
    Queue LogQueue(){
        return new Queue(queueLog);
    }

    @Bean
    public Binding bindingRegister(final Queue logQueue, final DirectExchange exchangeLog){
        return BindingBuilder.bind(logQueue).to(exchangeLog).with(logBindingKey);
    }
}
