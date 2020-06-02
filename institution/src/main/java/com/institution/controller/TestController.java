package com.institution.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.institution.model.Test;
import com.institution.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/institution")
public class TestController {

    Logger logger = LoggerFactory.getLogger(Test.class);

    @Autowired
    TestService testService;

    @PostMapping("/{institutionId}/test/saveAll")
    public List<Test> saveAllTests(@RequestBody @NotEmpty()
                                               List<@Valid Test> tests) {
        return testService.saveAllTest(tests);
    }

    @PostMapping("/{institutionId}/test/save")
    public Test saveTest(@RequestBody @Valid  Test test) {
        return testService.saveTest(test);
    }

    @GetMapping("/{institutionId}/test/{division}/{grade}")
    public List<Test> getTestsOfAGrade(@PathVariable(value= "institutionId") Long institutionId,
                                       @PathVariable(value = "division") String division,
                                       @PathVariable(value = "grade") String grade) {
        return testService.getGradeTest(institutionId, division, grade);
    }

    @PutMapping("/{institutionId}/test/{division}/{grade}")
    public Test updateTest(@RequestBody Test test,
                           @PathVariable(value= "institutionId") Long institutionId,
                           @PathVariable(value = "division") String division,
                           @PathVariable(value = "grade") String grade) throws JsonProcessingException {
        return  testService.upateTest(institutionId, division, grade, test);
    }

    @DeleteMapping("/{institutionId}/test/{division}/{grade}")
    public void deleteTest(
            @RequestBody Test test,
            @PathVariable(value= "institutionId") Long institutionId,
            @PathVariable(value = "division") String division,
            @PathVariable(value = "grade") String grade
    ) {
        testService.deleteTest(institutionId, test);
    }


}
