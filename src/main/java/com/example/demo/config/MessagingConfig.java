package com.example.demo.config;

import javax.jms.ConnectionFactory;

import com.example.demo.message.Consumer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;



@Configuration
public class MessagingConfig {
    Logger log;
    @Autowired
    private ConnectionFactory connectionFactory;
    @Value("${myqueue}")
    private String queue;
    @Bean
    public DefaultMessageListenerContainer messageListener() {
        DefaultMessageListenerContainer container = new
                DefaultMessageListenerContainer();
        container.setConnectionFactory(this.connectionFactory);
        container.setDestinationName(queue);
        container.setMessageListener(new Consumer());
        container.setErrorHandler(t -> {
            log.error("Error in listener!", t);
        });
        return container;
    }
}