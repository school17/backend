package com.institution.controller;


import com.institution.errorHandling.EntityNotFoundException;
import com.institution.model.Teacher;
import com.institution.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    Logger logger = LoggerFactory.getLogger(Teacher.class);

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create_teacher")
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherService.createTeacher(teacher);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{institutionId}/teachers")

    public List<Teacher> getAllTeacher(@PathVariable(value = "institutionId") Long institutionId) {
        return teacherService.getAllTeacher(institutionId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{institutionId}/teachers/{teacherId}")
    public Teacher updateTeacher(@RequestBody Teacher teacher, @PathVariable(value = "institutionId") Long institutionId, @PathVariable(value = "teacherId") Long Id)
            throws EntityNotFoundException
    {
        return  teacherService.updateTeacher(teacher);
    }


}
