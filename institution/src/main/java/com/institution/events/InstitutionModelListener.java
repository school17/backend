package com.institution.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

import com.institution.model.Institution;
import com.institution.service.SequenceGeneratorService;

public class InstitutionModelListener extends AbstractMongoEventListener<Institution> {
	
private SequenceGeneratorService sequenceGenerator;
	
	@Autowired
    public InstitutionModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Institution> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Institution.SEQUENCE_NAME));
        }
    }


}
