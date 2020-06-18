package com.institution.repository;


import com.institution.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, Long> {

    @Query("{'institutionId' : ?0, 'notificationType' : 'INSTITUTION', lastModified : { $gte : ?1}}")
    List<Notification> fetchGlobalNotification(long institutionId, Date date);

    @Query("{'institutionId' : ?0, 'notificationType' : 'DIVISION', 'division' : ?2 lastModified : { $gte : ?1}}")
    List<Notification> fetchDivisionNotification(long institutionId, Date date, String division);

    @Query("{'institutionId' : ?0, 'notificationType' : 'GRADE', 'grade' : ?2 lastModified : { $gte : ?1}}")
    List<Notification> fetchGradeNotification(long institutionId, Date date, String grade);

    @Query("{'institutionId' : ?0, 'notificationType' : 'PERSONAL', 'email' : ?2 lastModified : { $gte : ?1}}")
    List<Notification> fetchPersonalNotification(long institutionId, Date date, String email);

 }
