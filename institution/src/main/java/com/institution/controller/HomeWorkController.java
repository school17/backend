package com.institution.controller;

import com.institution.model.HomeWork;
import com.institution.service.HomeWorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/institution")
public class HomeWorkController {
    @Autowired
    HomeWorkService homeWorkService;

    Logger logger = LoggerFactory.getLogger(HomeWork.class);

    @PostMapping("{institutionId}/{grade}/{section}/homework")

    public HomeWork saveHomeWork(@RequestBody @Valid HomeWork homeWork) {
        return homeWorkService.saveHomeWork(homeWork);
    }


    @GetMapping("{institutionId}/{grade}/{section}/homework/{date}")

    public HomeWork getHomeWork(
            @PathVariable(value = "institutionId") Long institutionId,
            @PathVariable(value = "grade") String grade,
            @PathVariable(value = "section") String section,
            @PathVariable(value = "date") String date
    ) {
        return homeWorkService.findHomeWorkForADay(institutionId, grade, section, date);
    }

    @PutMapping("{institutionId}/{grade}/{section}/homework/{date}")
        public HomeWork updateHomeWork(@RequestBody @Valid HomeWork homeWork,
                                       @PathVariable(value = "institutionId") Long institutionId,
                                       @PathVariable(value = "grade") String grade,
                                       @PathVariable(value = "section") String section,
                                       @PathVariable(value = "date") String date) {
            return homeWorkService.updateHomeWork(homeWork);
        }
}
