package com.institution.events;

import com.institution.model.Notification;
import com.institution.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class NotificationModelListener  extends AbstractMongoEventListener<Notification> {
    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public NotificationModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Notification> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Notification.SEQUENCE_NAME));
        }
    }
}
