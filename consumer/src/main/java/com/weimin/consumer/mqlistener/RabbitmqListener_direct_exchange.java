package com.weimin.consumer.mqlistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class RabbitmqListener_direct_exchange {
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqListener_direct_exchange.class);

    // 使用注解的方式，声明队列、交换机和他们的绑定关系
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),// 监听的队列
            exchange = @Exchange(name = "direct.exchange", type = ExchangeTypes.DIRECT),// 队列绑定的交换机，默认就是Direct类型
            key = {"red", "blue"}// binding key
            // 消费者1，接收binding key为 red和blue的消息
    ))
    public void listenDirectQueue1(String message) {
        logger.info("消费者1收到来自direct.queue1消息: " + message + " " + LocalTime.now());
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "direct.exchange", type = ExchangeTypes.DIRECT),
            key = {"red", "yellow"}
            // 消费者2，接收binding key为 red和yellow的消息
    ))
    public void listenDirectQueue2(String message) {
        logger.info("消费者2收到来自direct.queue1消息: " + message + " " + LocalTime.now());
    }

}
