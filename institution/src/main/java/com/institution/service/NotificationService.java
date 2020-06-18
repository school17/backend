package com.institution.service;

import com.institution.model.Notification;

import java.util.List;
import java.util.Map;

public interface NotificationService {
    public List<Notification> getRecentNotifications(long institutionId, Map<String, String> searchParams);

    public Notification saveAndPublishNotification(Notification notification);
}
