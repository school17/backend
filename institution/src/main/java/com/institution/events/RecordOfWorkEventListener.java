package com.institution.events;

import com.institution.model.Notification;
import com.institution.model.RecordOfWork;
import com.institution.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class RecordOfWorkEventListener  extends AbstractMongoEventListener<RecordOfWork> {
    private final SequenceGeneratorService sequenceGenerator;
    @Autowired
    public RecordOfWorkEventListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<RecordOfWork> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(RecordOfWork.SEQUENCE_NAME));
        }
    }
}
