package com.institution.service;

import com.institution.model.Logwork;
import com.institution.repository.LogworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogworkServiceImpl implements  LogworkService {

    @Autowired
    private LogworkRepository logworkRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Override
    public Logwork getLogworkForADay(long institutionId, String grade, String section, String date) {
        return logworkRepository.findLogworkByInstitutionIdAndGradeAndSectionAndDate(institutionId, grade, section,date);
    }

    @Override
    public Logwork saveLogWork(Logwork logwork) {
        Logwork repoLogwork = logworkRepository.findLogworkByInstitutionIdAndGradeAndSectionAndDate(logwork.getInstitutionId(), logwork.getGrade(), logwork.getSection(),logwork.getDate());
        if (repoLogwork !=null) {
            logwork.setPersisted(true);
            logwork.setId(repoLogwork.getId());
            repoLogwork = logwork;
            return logworkRepository.save(repoLogwork);
        } else {
            logwork.setId(sequenceGenerator.generateSequence(Logwork.SEQUENCE_NAME));
            logwork.setPersisted(false);
            return logworkRepository.save(logwork);
        }

    }
}
