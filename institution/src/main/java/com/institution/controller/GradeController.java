package com.institution.controller;

import com.institution.model.Grade;
import com.institution.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{institutionId}/grades/search")

    public Page<Grade> findGrades(@RequestBody Grade grade,
                                  @PathVariable(value = "institutionId") Long institutionId,
                                  @RequestParam Map<String,String> searchParam)
    {
        return service.searchGrades(grade, institutionId, searchParam);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{institutionId}/grades/{gradeId}")
    public Grade updateGrade(@RequestBody Grade grade,
                             @PathVariable(value = "institutionId") Long institutionId,
                             @PathVariable(value = "gradeId") Long id)
    {
        return  service.updateGrade(grade, institutionId, id);
    }
}
