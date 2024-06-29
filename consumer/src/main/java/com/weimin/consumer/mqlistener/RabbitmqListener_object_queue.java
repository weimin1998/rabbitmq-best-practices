package com.weimin.consumer.mqlistener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class RabbitmqListener_object_queue {
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqListener_object_queue.class);

    @Bean
    public Queue objectQueue() {
        return new Queue("object.queue");
    }


    @RabbitListener(queues = "object.queue")
    // 方法参数可以直接写消息的具体类型，比如Map,或者自定义的类型
    public void listenSimpleQueue(Message message) {
        logger.info("收到消息: " + message);
        logger.info("收到消息: " + new String(message.getBody()));
        // 如果生产者没有消息转换器，直接发送了一个对象类型的消息，那么收到的消息就是：
        // 收到消息: (
        // Body:'[serialized object]'
        // MessageProperties [headers={},
        // contentType=application/x-java-serialized-object,
        // contentLength=0,
        // receivedDeliveryMode=PERSISTENT,
        // priority=0,
        // redelivered=true,
        // receivedExchange=,
        // receivedRoutingKey=object.queue,
        // deliveryTag=1,
        // consumerTag=amq.ctag-GNEgNAk4sypBabuzzWfLpg,
        // consumerQueue=object.queue])
    }
}
