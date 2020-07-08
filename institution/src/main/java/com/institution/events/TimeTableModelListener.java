package com.institution.events;

import com.institution.model.grade.SubjectTeacher;
import com.institution.model.grade.TimeTable;
import com.institution.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class TimeTableModelListener extends AbstractMongoEventListener<TimeTable> {
    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public TimeTableModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<TimeTable> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(TimeTable.SEQUENCE_NAME));
        }
    }
}
