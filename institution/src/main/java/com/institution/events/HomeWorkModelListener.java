package com.institution.events;

import com.institution.model.HomeWork;
import com.institution.model.grade.TimeTable;
import com.institution.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class HomeWorkModelListener extends AbstractMongoEventListener<HomeWork> {

    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public HomeWorkModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<HomeWork> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(HomeWork.SEQUENCE_NAME));
        }
    }
}
