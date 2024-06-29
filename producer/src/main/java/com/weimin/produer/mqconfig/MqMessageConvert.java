package com.weimin.produer.mqconfig;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqMessageConvert {

    @Bean
    public MessageConverter messageConverter(){
        // 将java对象类型的消息，转为json
        return new Jackson2JsonMessageConverter();
    }
}
