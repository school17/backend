package com.institution.controller;

import com.institution.model.Attendance;
import com.institution.service.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/institution")
public class AttendanceController {

    Logger logger = LoggerFactory.getLogger(Attendance.class);

    @Autowired
    AttendanceService attendanceService;

    @PostMapping("/{institutionId}/attendance/save")
    public Attendance saveAttendance(@RequestBody @Valid Attendance attendance) {
        return attendanceService.saveAttendance(attendance);
    }

    @GetMapping("/{institutionId}/attendance/{grade}/{section}/{month}/{year}")

    public List<Attendance> getAttendance(@PathVariable(value = "institutionId") Long institutionId,
                                          @PathVariable(value = "grade") String grade,
                                          @PathVariable(value = "section") String section,
                                          @PathVariable(value = "month") String month,
                                          @PathVariable(value = "year") String year) {
        return attendanceService.findAttendance(institutionId,grade,section,month,year);
    }

    @PostMapping("/{institutionId}/attendance/bulksave")
    public ResponseEntity<String> saveBulkAttendance(@RequestBody @Valid Attendance attendance) {
        attendanceService.saveBulkAttendance(attendance);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
