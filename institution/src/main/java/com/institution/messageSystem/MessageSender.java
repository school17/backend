package com.institution.messageSystem;
import com.institution.model.Notification;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    ConnectionFactory connectionFactory;
    Connection connection;
    Channel channel;

    MessageSender() {
        this.connectionFactory = new ConnectionFactory();
        try{
            this.connectionFactory.setUri("amqp://guest:guest@localhost:5672");
            this.connectionFactory.setConnectionTimeout(300000);
            this.connection = connectionFactory.newConnection();
            this.channel = connection.createChannel();
            this.channel.queueDeclare("upload-queue", true, false, false, null);
            this.channel.queueDeclare("notification", true, false, false, null);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    /*private RabbitTemplate rabbitTemplate;
    @Autowired
    public MessageSender(final RabbitTemplate template){
        this.rabbitTemplate = template;
    }*/

    class Message {


        public String getInstitution() {
            return institution;
        }

        public void setInstitution(String institution) {
            this.institution = institution;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        String institution;
        String message;

        Message(){

        }

        Message(String institution, String message) {
            this.institution  = institution;
            this.message = message;
        }
    }

    public void sendMessage(String message) {
        Message message1 = new Message("3", "Notification");
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend("upload-queue", message1);

       /* ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            //connectionFactory.setUri("amqp://guest:guest@host.docker.internal:5672");
            connectionFactory.setUri("amqp://guest:guest@localhost:5672");
            //connectionFactory.setUri("amqp://guest:guest@13.58.111.197:5672/%2F");
            connectionFactory.setConnectionTimeout(300000);
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare("notification", true, false, false, null);
            Message message1 = new Message("3", "Notification");
            //channel.basicPublish("", "upload-queue", null, message1.toString().getBytes());
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.convertAndSend("upload-queue", message1);
        }catch(Exception e) {
            e.printStackTrace();
        }*/


    }

    public void sendNotification(Notification notification) {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend("notification", notification);
    }
}
