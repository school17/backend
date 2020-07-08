package com.institution.controller;

import com.institution.model.grade.TimeTable;
import com.institution.service.TimeTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/institution")
public class TimeTableController {

    @Autowired
    TimeTableService timeTableService;

    Logger logger = LoggerFactory.getLogger(TimeTable.class);

    @PostMapping("{institutionId}/{grade}/{section}/timetable")
    public TimeTable saveTimeTable(@RequestBody @Valid TimeTable timeTable,
                                   @PathVariable(value = "institutionId") Long institutionId,
                                   @PathVariable(value = "grade") String grade,
                                   @PathVariable(value = "section") String section) {
        return timeTableService.saveTimeTable(timeTable);

    }

    @GetMapping("{institutionId}/{grade}/{section}/timetable")
    public TimeTable getTimeTable(@PathVariable(value = "institutionId") Long institutionId,
                                  @PathVariable(value = "grade") String grade,
                                  @PathVariable(value = "section") String section) {
        return timeTableService.getTimeTable(institutionId, grade, section);

    }

    @PutMapping("{institutionId}/{grade}/{section}/timetable/{id}")
    public TimeTable updateTimeTable(@RequestBody @Valid TimeTable timeTable,
            @PathVariable(value = "institutionId") Long institutionId,
            @PathVariable(value = "grade") String grade,
            @PathVariable(value = "section") String section,
                                     @PathVariable(value = "id") long id
    ) {
        return timeTableService.updateTimeTable(institutionId, grade, section, timeTable, id);
    }
}
