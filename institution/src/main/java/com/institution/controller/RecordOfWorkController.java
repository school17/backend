package com.institution.controller;

import com.institution.model.RecordOfWork;
import com.institution.service.RecordOfWorkService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/institution")
public class RecordOfWorkController {

    @Autowired
    RecordOfWorkService recordOfWorkService;

    Logger logger = LoggerFactory.getLogger(RecordOfWork.class);

    @PostMapping("{institutionId}/{grade}/{division}/addrecordofwork")

    public RecordOfWork saveHomeWork(@RequestBody @Valid RecordOfWork recordOfWork) {
        return recordOfWorkService.saveRecordOfWork(recordOfWork);
    }


    @GetMapping("{institutionId}/{grade}/{division}/recordofwork")

    public RecordOfWork getRecordOfWork(
            @PathVariable(value = "institutionId") Long institutionId,
            @PathVariable(value = "grade") String grade,
            @PathVariable(value = "division") String division,
            @PathVariable(value = "subject") String subject
    ) {
        return recordOfWorkService.findRecordOfWorks(institutionId, grade, division, subject);
    }

    @PutMapping("{institutionId}/{grade}/{division}/updaterecordofwork")
    public RecordOfWork getRecordOfWork(@RequestBody @Valid RecordOfWork recordOfWork,
                                   @PathVariable(value = "institutionId") Long institutionId,
                                   @PathVariable(value = "grade") String grade,
                                   @PathVariable(value = "division") String division,
                                   @PathVariable(value = "subject") String subject) {
        return recordOfWorkService.updateRecordOfWork(recordOfWork);
    }

}
