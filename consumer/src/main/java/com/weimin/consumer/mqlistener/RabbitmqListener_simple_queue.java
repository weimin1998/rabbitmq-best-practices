package com.weimin.consumer.mqlistener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqListener_simple_queue {
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqListener_simple_queue.class);

    // 简单队列模型，监听 simple.queue这个队列
    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(String message) {
        logger.info("收到消息: " + message);
    }
}
