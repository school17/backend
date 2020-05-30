package com.institution.events;

import com.institution.model.Attendance;
import com.institution.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class AttendanceListener  extends AbstractMongoEventListener<Attendance> {

    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public AttendanceListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Attendance> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Attendance.SEQUENCE_NAME));
        }
    }
}
