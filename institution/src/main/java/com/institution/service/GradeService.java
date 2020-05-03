package com.institution.service;


import com.institution.model.Grade;
import com.institution.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface GradeService {
    public Grade createGrade(Grade grade);

    public List<Grade> listAllGrades(String institutionId);

    public Page<Grade> searchGrades(Grade grade, long institutionId, Map<String,String> searchParam);

    public Grade updateGrade(Grade grade, Long institutionId, Long id);
}
