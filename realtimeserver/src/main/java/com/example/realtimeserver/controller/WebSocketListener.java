package com.example.realtimeserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class WebSocketListener {

	 @Autowired
	 private SimpMessageSendingOperations messagingTemplate;
	 
	 @EventListener
	    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
	        System.out.println("Received a new web socket connection");
	 }
	 
	 
}
