package com.institution.events;

import com.institution.model.ApplicationUser;
import com.institution.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class ApplicationUserModelListener extends AbstractMongoEventListener<ApplicationUser> {
    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public ApplicationUserModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<ApplicationUser> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(ApplicationUser.SEQUENCE_NAME));
        }
    }
}
