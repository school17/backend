package com.institution.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.institution.model.test.Test;

import java.util.List;

public interface TestService {

    public List<Test> saveAllTest(List<Test> test);

    public Test saveTest(Test test);

    public List<Test> getGradeTest(Long institutionId, String division, String grade);

    public Test upateTest(Long institutionId, String division, String grade, Test test) throws JsonProcessingException;

    public void deleteTest(Long institutionId, Test test);
}
