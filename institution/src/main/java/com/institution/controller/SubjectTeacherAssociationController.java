package com.institution.controller;

import com.institution.model.grade.SubjectTeacher;
import com.institution.service.SubjectTeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/institution")
public class SubjectTeacherAssociationController {

    Logger logger = LoggerFactory.getLogger(SubjectTeacher.class);

    @Autowired
    SubjectTeacherService subjectTeacherService;

    @PostMapping("{institutionId}/associate_subject_teacher")
    public SubjectTeacher saveSubjectTeacherAssociation(@RequestBody @Valid SubjectTeacher subjectTeacher) {
        return subjectTeacherService.saveSubjectTeacherAssociation(subjectTeacher);
    }

    @GetMapping("{institutionId}/associate_subject_teacher/{grade}/{section}")
    public SubjectTeacher getSubjectTeacherAssociation(@PathVariable(value = "institutionId") Long institutionId,
                                                       @PathVariable(value = "grade") String grade,
                                                       @PathVariable(value = "section") String section) {
        return subjectTeacherService.findSubjectTeacherAssociation(institutionId, grade, section);
    }


    @PutMapping("{institutionId}/associate_subject_teacher/{id}/{grade}/{section}")
    public SubjectTeacher updateSubjectTeacherAssociation(@RequestBody @Valid SubjectTeacher subjectTeacher,
                                                          @PathVariable(value = "institutionId") Long institutionId,
                                                          @PathVariable(value = "grade") String grade,
                                                          @PathVariable(value = "section") String section,
                                                          @PathVariable(value = "id") Long id) {
        return subjectTeacherService.updateSubjectTeacherAssociation(subjectTeacher, institutionId, grade, section, id);
    }


}
