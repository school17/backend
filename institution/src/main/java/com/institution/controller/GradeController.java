package com.institution.controller;

import com.institution.model.Grade;
import com.institution.model.Teacher;
import com.institution.repository.GradeRepository;
import com.institution.service.GradeService;
import com.institution.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/institution")
public class GradeController {

    @Autowired
    GradeService service;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{institutionId}/create_grade")
    public Grade createGrade(@RequestBody Grade grade){

        return service.createGrade(grade);
    }

    @GetMapping("{institutionId}/grades")
    public List<Grade> getGrades(@PathVariable(value = "institutionId") String institutionId) {
        return service.listAllGrades(institutionId);
    }

    /*@GetMapping("{institutionId}/grades/{section}")
    public List<Grade> getGradeBySection(@PathVariable(value = "institutionId") String institutionId,
                                         @PathVariable(value = "section") String section){
        return repo.findGradeByInstitutionIdAndSection(institutionId, section);

    }*/
}
