package com.example.realtimeserver;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.sound.midi.Receiver;

@SpringBootApplication
@CrossOrigin(origins = "*")
public class Realtimeserver1Application {

	//Use this bean for deployment
	/*@Bean
	public CachingConnectionFactory rabbitConnectionFactory(RabbitProperties config) throws Exception{
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.getRabbitConnectionFactory().setUri("amqp://guest:guest@13.58.111.197:5672/%2F");
		return  connectionFactory;
	}*/

	public static void main(String[] args) {
		SpringApplication.run(Realtimeserver1Application.class, args);
	}

}
