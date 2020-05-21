package com.institution;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableMongoAuditing
@EnableScheduling
public class InstitutionApplication {

	public static final String EXCHANGE_NAME ="demo_exchange";
	public static final String QUEUE_NAME ="demo_queue";
	public static final String ROUTING_NAME ="demo";

	public static void main(String[] args) {
		SpringApplication.run(InstitutionApplication.class, args);
	}
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

	/*@Bean
	Queue queue(){return new Queue("demo-queue", false);}

	@Bean
	TopicExchange exchange() {return  new TopicExchange("demo-exchange");}

	@Bean
	Binding binding(Queue queue, TopicExchange topicExchange){
		return  BindingBuilder.bind(queue).to(topicExchange).with("demo-queue");
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames("demo-queue");
		return container;
	}*/
}
