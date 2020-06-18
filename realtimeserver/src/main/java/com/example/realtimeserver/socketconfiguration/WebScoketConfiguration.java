package com.example.realtimeserver.socketconfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebScoketConfiguration implements WebSocketMessageBrokerConfigurer {
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/websocketApp").setAllowedOrigins("*").withSockJS();
	}
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app");
		registry.enableStompBrokerRelay("/topic")
				//.setRelayHost("13.58.111.197")
				.setRelayHost("localhost")
				.setRelayPort(61613).setClientLogin("guest")
				.setClientPasscode("guest");
	}

}
