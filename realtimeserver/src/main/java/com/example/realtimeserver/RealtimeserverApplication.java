package com.example.realtimeserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:3006")
public class RealtimeserverApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(RealtimeserverApplication.class, args);
	}

}
