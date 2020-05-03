package com.institution.controller;


import com.institution.errorHandling.EntityNotFoundException;
import com.institution.model.Teacher;
import com.institution.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/institution")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    Logger logger = LoggerFactory.getLogger(Teacher.class);

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{institutionId}/create_teacher")
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{institutionId}/teachers/search")
    public Page<Teacher> findTeachers(@RequestBody Teacher teacher,
                                      @PathVariable(value = "institutionId") Long institutionId,
                                      @RequestParam Map<String,String> searchParam) {

        return teacherService.searchTeachers(teacher, institutionId, searchParam);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{institutionId}/teachers/available_teachers")
    public List<Teacher> availableTeacher(@PathVariable(value = "institutionId") Long institutionId) {
        return teacherService.findNonClassTeachers(institutionId);
    }

    @GetMapping("/{institutionId}/teachers/{name}/{grade}/grades_teacher")

    public Optional<Teacher> getTeacherByNameAndGrade(@PathVariable(value = "institutionId") Long institutionId,
                                                      @PathVariable(value = "name") String name,
                                                      @PathVariable(value = "grade") String grade){

        return teacherService.findTeacherByGrade(institutionId, grade, name);

    }


}
