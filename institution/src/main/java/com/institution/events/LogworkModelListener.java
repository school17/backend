package com.institution.events;

import com.institution.model.Logwork;
import com.institution.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class LogworkModelListener extends AbstractMongoEventListener<Logwork> {
    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public LogworkModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Logwork> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Logwork.SEQUENCE_NAME));
        }
    }
}
