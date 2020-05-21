package com.institution.model.grade;

import com.institution.model.grade.Section;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class DivisionGrade {
    private String grade;
    private Set<Section> section;

    @org.springframework.data.annotation.Transient
    private String numberOfSections;

    public DivisionGrade(){}
}