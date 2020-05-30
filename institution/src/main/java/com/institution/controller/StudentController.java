package com.institution.controller;


import com.institution.model.Student;
import com.institution.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/institution")
public class StudentController {

    Logger logger = LoggerFactory.getLogger(Student.class);

    @Autowired
    StudentService studentService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{institutionId}/students/create_student")
    public Student createStudent(@RequestBody Student student,
                                 @PathVariable(value = "institutionId") Long institutionId) {
        return  studentService.createStudent(student, institutionId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{institutionId}/students/search_students")
    public Page<Student> searchStudents(@RequestBody Student student,
                                        @PathVariable(value = "institutionId") Long institutionId,
                                        @RequestParam Map<String,String> searchParam) {
        return studentService.searchStudent(student, institutionId, searchParam);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{institutionId}/students/{studentId}")

    public Student updateStudent(
            @Valid @RequestBody Student student,
            @PathVariable(value = "institutionId") Long institutionId,
            @PathVariable(value = "studentId") Long studentId
    ) {
        return studentService.updateStudent(student, institutionId, studentId);
    }


    @GetMapping("/{institutionId}/students/{grade}/{section}")

    public List<Student> getGradeStudents(
            @PathVariable(value = "institutionId") Long institutionId,
            @PathVariable(value = "grade") String grade,
            @PathVariable(value = "section") String section
    ){
     return studentService.getStudentByGradeAndSection(institutionId,grade,section);
    }

}
