package com.example.realtimeserver.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:3006")
@RestController
public class RealtimeController {
	
	@Autowired
    private SimpMessageSendingOperations messagingTemplate;
	
	@GetMapping("/")
	public String sendMessage() {
		String ins = "1";
		messagingTemplate.convertAndSend("/topic/global", "This is a global message");
		
		return "SENT";
		
	}
	
	//@RabbitListener(queues = "upload-queue")
	/*@SendTo("/topic/global")
	public String sendMessageToClient() {
		System.out.println("THIS IS WHERE IT GOT");
		return "SENT FROM THE SERVER";
	}*/
	
	@RabbitListener(queues = "upload-queue")
    public void receiveMessage() {
       System.out.println("Received message as specific class: {}");
    }

}
