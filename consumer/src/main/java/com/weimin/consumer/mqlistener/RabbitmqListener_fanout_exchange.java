package com.weimin.consumer.mqlistener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class RabbitmqListener_fanout_exchange {
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqListener_fanout_exchange.class);

    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String message) {
        logger.info("消费者收到来自fanout.queue1消息: " + message + " " + LocalTime.now());
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String message) {
        logger.info("消费者收到来自fanout.queue2消息: " + message + " " + LocalTime.now());
    }
}
