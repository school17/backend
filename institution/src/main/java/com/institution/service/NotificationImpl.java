package com.institution.service;

import com.institution.messageSystem.MessageSender;
import com.institution.model.Notification;
import com.institution.model.Student;
import com.institution.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NotificationImpl implements  NotificationService{

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    PushNotification pushNotification;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Autowired
    MessageSender messageSender;

    @Override
    public List<Notification> getRecentNotifications(long institutionId, Map<String, String> searchParams) {
        Date date = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 10 * 30);
        Comparator<Notification> compareByDate = (Notification n1, Notification n2 ) -> n1.getLastModified().compareTo( n2.getLastModified());
        List<Notification> notifications = new ArrayList<>();
        notifications.addAll(notificationRepository.fetchGlobalNotification(institutionId, date));
        notifications.addAll(notificationRepository.fetchDivisionNotification(institutionId, date, searchParams.get("?division")));
        notifications.addAll(notificationRepository.fetchGradeNotification(institutionId, date, searchParams.get("grade")));
        notifications.addAll(notificationRepository.fetchPersonalNotification(institutionId, date, searchParams.get("?email")));
        Collections.sort(notifications, compareByDate.reversed());
        return notifications;
    }

    @Override
    public Notification saveAndPublishNotification(Notification notification) {
        notification.setId(sequenceGenerator.generateSequence(Notification.SEQUENCE_NAME));
        notification.setPersisted(false);
        messageSender.sendNotification(notification);
        return notificationRepository.save(notification);
    }
}