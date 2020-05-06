package com.institution.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.beans.Transient;
import java.util.Set;

@Data
@AllArgsConstructor
public class AvailableGradesAndSections {
    private String division;
    private Set<DivisionGrade> divisionGrade;

    public AvailableGradesAndSections() {

    }
}