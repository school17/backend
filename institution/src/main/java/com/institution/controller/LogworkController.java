package com.institution.controller;


import com.institution.model.Logwork;
import com.institution.service.LogworkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/institution")
public class LogworkController {
    @Autowired
    LogworkService logworkService;
    Logger logger = LoggerFactory.getLogger(Logwork.class);

    @GetMapping("{institutionId}/{grade}/{section}/logwork/{date}")

    public Logwork getLogwork(
            @PathVariable(value = "institutionId") Long institutionId,
            @PathVariable(value = "grade") String grade,
            @PathVariable(value = "section") String section,
            @PathVariable(value = "date") String date
    ) {
        return logworkService.getLogworkForADay(institutionId, grade, section, date);
    }

    @PostMapping("{institutionId}/{grade}/{section}/logwork")
    public Logwork saveLogwork(@RequestBody @Valid Logwork logwork) {
        return  logworkService.saveLogWork(logwork);
    }
}
