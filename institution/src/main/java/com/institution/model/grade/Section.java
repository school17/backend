package com.institution.model.grade;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Section {
    private String section;
    private boolean isCreated;

    public Section(){

    }
}
