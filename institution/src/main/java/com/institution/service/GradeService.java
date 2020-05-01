package com.institution.service;


import com.institution.model.Grade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GradeService {
    public Grade createGrade(Grade grade);

    public List<Grade> listAllGrades(String institutionId);
}
