package com.example.realtimeserver.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Notification {

    private long institutionId;

    private Date createdAt;

    private Date lastModified;

    private boolean persisted;

    private boolean isNew;


    private String notificationType;

    private long id;

    private String division;

    private String grade;

    private String section;

    private String email;

    private String message;

    private  boolean hasFeedback;
}
