package com.institution.events;

import com.institution.model.Teacher;
import com.institution.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class TeacherModelListener extends AbstractMongoEventListener<Teacher> {
    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public TeacherModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Teacher> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Teacher.SEQUENCE_NAME));
        }
    }
}
