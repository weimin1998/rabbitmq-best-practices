package com.weimin.produer.web;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/producer")
public class ProducerController {

    private static final Logger logger = LoggerFactory.getLogger(ProducerController.class);

    // http://localhost:8080/producer/sendSimpleMsg
    // 消息生产者，使用java api 发送消息到队列
    @GetMapping("/sendSimpleMsg")
    public String simple() throws IOException, TimeoutException {
        return sendMsg();
    }

    private String sendMsg() throws IOException, TimeoutException {
        // 1.建立连接
        ConnectionFactory factory = new ConnectionFactory();
        // 1.1.设置连接参数，分别是：主机名、端口号、vhost、用户名、密码
        factory.setHost("110.40.128.244");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("weimin");
        factory.setPassword("weimin");
        // 1.2.建立连接
        Connection connection = factory.newConnection();

        // 2.创建通道Channel
        Channel channel = connection.createChannel();

        // 3.创建队列
        String queueName = "simple.queue";
        channel.queueDeclare(queueName, false, false, false, null);

        // 4.发送消息
        String message = "hello, rabbitmq!";
        channel.basicPublish("", queueName, null, message.getBytes());
        System.out.println("发送消息成功：【" + message + "】");

        // 5.关闭通道和连接
        channel.close();
        connection.close();

        return "发送成功";
    }


    @Resource
    RabbitTemplate rabbitTemplate;

    // http://localhost:8080/producer/sendSimpleMsg_amqp
    // 消息生产者，使用spring-amqp 发送消息到队列
    // simple queue
    @GetMapping("/sendSimpleMsg_amqp")
    public String simpleAmqp(@RequestParam(required = false, defaultValue = "hello, spring amqp!") String msg) {
        String queueName = "simple.queue";
        rabbitTemplate.convertAndSend(queueName, msg);
        logger.info("发送消息：" + msg);
        return "发送成功";
    }


    // http://localhost:8080/producer/sendWorkQueue
    // 消息生产者，使用spring-amqp 发送消息到队列
    // work queue
    @GetMapping("/sendWorkQueue")
    public String sendWorkQueue(@RequestParam(required = false, defaultValue = "hello, spring amqp!") String msg) throws InterruptedException {
        String queueName = "work.queue";

        // 每隔20ms发送一条消息，1s钟发送50条
        for (int i = 1; i <= 50; i++) {
            rabbitTemplate.convertAndSend(queueName, msg + "_" + i);
            Thread.sleep(20);
        }
        return "发送成功";
    }

    // http://localhost:8080/producer/sendToFanoutExchange
    // 消息生产者，使用spring-amqp 发送消息到交换机
    // fanout exchange
    @GetMapping("/sendToFanoutExchange")
    public String sendToFanoutExchange(@RequestParam(required = false, defaultValue = "hello, everyone!") String msg) {
        String exchange = "fanout.exchange";
        rabbitTemplate.convertAndSend(exchange, "", msg);
        return "发送成功";
    }


    // http://localhost:8080/producer/sendToDirectExchange
    // 消息生产者，使用spring-amqp 发送消息到交换机
    // direct exchange
    @GetMapping("/sendToDirectExchange")
    public String sendToDirectExchange() throws InterruptedException {
        String exchange = "direct.exchange";
        String message1 = "hello, blue";
        String message2 = "hello, yellow";
        String message3 = "hello, red";
        rabbitTemplate.convertAndSend(exchange, "blue", message1);
        Thread.sleep(10);
        rabbitTemplate.convertAndSend(exchange, "yellow", message2);
        Thread.sleep(10);
        rabbitTemplate.convertAndSend(exchange, "red", message3);
        return "发送成功";
    }

    // http://localhost:8080/producer/sendToTopicExchange
    // 消息生产者，使用spring-amqp 发送消息到交换机
    // topic exchange
    @GetMapping("/sendToTopicExchange")
    public String sendToTopicExchange() throws InterruptedException {
        String exchange = "topic.exchange";
        String message1 = "中国新闻";
        String message2 = "中国天气";
        String message3 = "日本新闻";

        // topic交换机，routingkey必须是多个单词，并且以点分隔
        rabbitTemplate.convertAndSend(exchange, "china.news", message1);
        Thread.sleep(10);
        rabbitTemplate.convertAndSend(exchange, "china.weather", message2);
        Thread.sleep(10);
        rabbitTemplate.convertAndSend(exchange, "japan.news", message3);
        return "发送成功";
    }


    // http://localhost:8080/producer/sendObjectMsg
    // 消息生产者，使用spring-amqp 发送消息到队列
    // 这次发送的消息类型是java对象
    @GetMapping("/sendObjectMsg")
    public String sendObjectMsg() {

        String queue = "object.queue";
        Map<String,String> map = new HashMap<>();
        map.put("name","weimin");
        rabbitTemplate.convertAndSend(queue,map);
        return "发送成功";
    }
}
