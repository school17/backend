package com.institution.messageSystem;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageSender {

    /*private RabbitTemplate rabbitTemplate;
    @Autowired
    public MessageSender(final RabbitTemplate template){
        this.rabbitTemplate = template;
    }*/

    public void sendMessage(String message) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try {
            connectionFactory.setUri("amqp://guest:guest@host.docker.internal:5672");
            connectionFactory.setConnectionTimeout(300000);
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare("upload-queue", true, false, false, null);
            String demo = "Uploaded";
            channel.basicPublish("", "upload-queue", null, demo.getBytes());
        }catch(Exception e) {
            e.printStackTrace();
        }


    }
}
