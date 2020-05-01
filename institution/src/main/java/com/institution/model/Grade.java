package com.institution.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "grades")
@Getter
@Setter
@NoArgsConstructor
public class Grade extends CustomAuditing {

    @Transient
    public static final String SEQUENCE_NAME = "application_grades_sequence";

    @Id
    private long id;

    private String grade;

    private String section;

    private String teacher;

    private String strength;

    private String division;

    private String institutionId;


}
