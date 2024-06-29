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
public class RabbitmqListener_topic_exchange {
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqListener_topic_exchange.class);

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue1"),
            exchange = @Exchange(name = "topic.exchange", type = ExchangeTypes.TOPIC),//设置类型为topic
            key = "china.#"
    ))
    public void listenTopicQueue1(String message) {
        logger.info("消费者1收到来自topic.queue1消息: " + message + " " + LocalTime.now());
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue2"),
            exchange = @Exchange(name = "topic.exchange", type = ExchangeTypes.TOPIC),
            key = "#.news"
    ))
    public void listenTopicQueue2(String message) {
        logger.info("消费者2收到来自topic.queue2消息: " + message + " " + LocalTime.now());
    }

}
