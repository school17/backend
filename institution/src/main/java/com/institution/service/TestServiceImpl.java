package com.institution.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.institution.errorHandling.EntityNotFoundException;
import com.institution.model.Student;

import com.institution.model.test.Test;

import com.institution.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestRepository testRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Override
    public List<Test> saveAllTest(List<Test> tests) {
        for(Test test: tests) {
            test.setId(sequenceGenerator.generateSequence(Test.SEQUENCE_NAME));
        }
        return testRepository.saveAll(tests);
    }

    @Override
    public Test saveTest(Test test) {
        test.setId(sequenceGenerator.generateSequence(Test.SEQUENCE_NAME));
        return testRepository.save(test);
    }

    @Override
    public List<Test> getGradeTest(Long institutionId, String division, String grade) {
        return testRepository.findTestByInstitutionIdAndDivisionAndGrade(institutionId, division,grade);
    }

    @Override
    public Test upateTest(Long institutionId, String division, String grade, Test test) throws JsonProcessingException {
        Optional<Test> testOptional = testRepository.findTestByInstitutionIdAndId(institutionId, test.getId());
        if(testOptional == null)
            throw new EntityNotFoundException(Student.class, "id", Long.toString(test.getId()));
        else{
            Test t = testOptional.get();
            t = test;
            return testRepository.save(t);
        }
    }

    @Override
    public void deleteTest(Long institutionId, Test test) {
        Optional<Test> testOptional = testRepository.findTestByInstitutionIdAndId(institutionId, test.getId());
        if(testOptional == null)
            throw new EntityNotFoundException(Student.class, "id", Long.toString(test.getId()));
        else {
            testRepository.delete(testOptional.get());
        }
    }
}
