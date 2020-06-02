package com.institution.events;

import com.institution.model.test.Test;
import com.institution.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class TestModelListener  extends AbstractMongoEventListener<Test> {

    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public TestModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Test> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Test.SEQUENCE_NAME));
        }
    }
}
