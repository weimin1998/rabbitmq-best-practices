package com.weimin.produer.web;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/producer")
public class ProducerController {

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
}
