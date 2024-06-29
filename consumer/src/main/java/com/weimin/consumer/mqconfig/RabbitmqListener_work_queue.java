package com.weimin.consumer.mqconfig;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class RabbitmqListener_work_queue {
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqListener_work_queue.class);

    // 使用spring-amqp，队列需要手动在页面创建，不会自动创建

    // 工作队列模型，监听 work.queue这个队列
    // 该消费者，处理一次消息需要20ms，1s可以处理1000/20=50 条消息
    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue1(String message) {
        logger.info("消费者1收到消息: " + message + " " + LocalTime.now());
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 工作队列模型，监听 work.queue这个队列
    // 该消费者，处理一次消息需要200ms，1s可以处理1000/200=5 条消息
    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue2(String message) {
        logger.error("消费者2收到消息: " + message + " " + LocalTime.now());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
