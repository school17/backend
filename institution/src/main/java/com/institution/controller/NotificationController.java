package com.institution.controller;

import com.institution.model.Notification;
import com.institution.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/institution")
public class NotificationController {
    Logger logger = LoggerFactory.getLogger(Notification.class);

    @Autowired
    NotificationService notificationService;

    @PostMapping("{institutionId}/notification")
    Notification saveNotification(@RequestBody @Valid Notification notification,
                                  @PathVariable(value = "institutionId") Long institutionId) {
        return notificationService.saveAndPublishNotification(notification);
    }

    @GetMapping("{institutionId}/notification")
    List<Notification> getNotification(
                                       @PathVariable(value = "institutionId") Long institutionId,
                                       @RequestParam Map<String,String> searchParam) {
        System.out.println("searchParam" + searchParam.get("email"));
        return notificationService.getRecentNotifications(institutionId, searchParam);
    }


}
