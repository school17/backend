package com.institution.model.grade;

import com.institution.model.grade.DivisionGrade;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class AvailableGradesAndSections {
    private String division;
    private Set<DivisionGrade> divisionGrade;

    public AvailableGradesAndSections() {

    }
}