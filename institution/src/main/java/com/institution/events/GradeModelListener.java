package com.institution.events;

import com.institution.model.Grade;
import com.institution.model.Teacher;
import com.institution.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class GradeModelListener  extends AbstractMongoEventListener<Grade> {
    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public GradeModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Grade> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Grade.SEQUENCE_NAME));
        }
    }
}
