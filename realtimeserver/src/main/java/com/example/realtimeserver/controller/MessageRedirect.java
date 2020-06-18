package com.example.realtimeserver.controller;


import com.example.realtimeserver.model.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
public class MessageRedirect {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    /*@RabbitListener(queues = "upload-queue")
    @SendTo("/{institution}/notification")
    public String sendNotification(String notification) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Notification not = objectMapper.readValue(notification, Notification.class);
        messagingTemplate.convertAndSend("/topic/"+not.getInstitution() + ".student", not.getMessage());
        return "NOTIFICATION SENT";
    }*/

    @RabbitListener(queues = "notification")
    //@SendTo("/{institution}/notification")
    public void publishNotification(String notification) throws JsonProcessingException {
        System.out.println("notification" + notification);
        ObjectMapper objectMapper = new ObjectMapper();
        Notification not = objectMapper.readValue(notification, Notification.class);
        System.out.println("not.getNotificationType()" + not.getNotificationType().equalsIgnoreCase("DIVISION"));
        if(not.getNotificationType().equalsIgnoreCase("INSTITUTION")) {
            messagingTemplate.convertAndSend("/topic/"+not.getInstitutionId(), not.getMessage());
        }
        if(not.getNotificationType().equalsIgnoreCase("DIVISION")) {
            System.out.println("/topic/"+not.getInstitutionId() + "."
                    + not.getDivision());
            messagingTemplate.convertAndSend("/topic/"+not.getInstitutionId() + "."
                    + not.getDivision(), not.getMessage());
        }

        if(not.getNotificationType().equalsIgnoreCase("GRADE")) {
            messagingTemplate.convertAndSend("/topic/"+not.getInstitutionId() + "."
                    + not.getDivision() + "."+ not.getGrade(), not.getMessage());
        }

        if(not.getNotificationType().equalsIgnoreCase("PERSONAL")) {
            messagingTemplate.convertAndSend("/topic/"+not.getInstitutionId() + "."
                    +not.getEmail(), not.getMessage());
        }
    }
}
