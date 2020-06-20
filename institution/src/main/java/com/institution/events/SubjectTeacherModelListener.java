package com.institution.events;

import com.institution.model.grade.SubjectTeacher;
import com.institution.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class SubjectTeacherModelListener  extends AbstractMongoEventListener<SubjectTeacher> {

    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public SubjectTeacherModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<SubjectTeacher> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(SubjectTeacher.SEQUENCE_NAME));
        }
    }
}
