package com.institution.service;

import com.institution.model.Grade;
import com.institution.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    GradeRepository repo;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Override
    public Grade createGrade(Grade grade) {
        grade.setId(sequenceGenerator.generateSequence(Grade.SEQUENCE_NAME));
        return repo.save(grade);
    }

    @Override
    public List<Grade> listAllGrades(String institutionId) {
        return repo.findGradeByInstitutionId(institutionId);
    }
}
