package com.institution.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification extends CustomAuditing implements Persistable<Long> {

    @Transient
    public static final String SEQUENCE_NAME = "application_notification_sequence";
    private boolean persisted;

    @NotNull
    private long institutionId;

    @NotNull
    private String notificationType;

    private long id;
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return !persisted;
    }

    private String division;

    private String grade;

    private String section;

    private String email;

    private String message;

    private  boolean hasFeedback;
}
