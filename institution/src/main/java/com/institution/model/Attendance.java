package com.institution.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "attendance")
@Getter
@Setter
@NoArgsConstructor
public class Attendance {

    @Transient
    public static final String SEQUENCE_NAME = "application_attendance_sequence";

    private long id;

    @NonNull
    private long institutionId;

    private String name;

    @Transient
    private Set<String> studentsList;

    @NonNull
    private String month;

    @NonNull
    private String date;

    @NonNull
    private String grade;

    @NonNull
    private String section;

    @NonNull
    private String year;

    public Attendance(long institutionId, String date, String month, String year, String grade, String section, String name) {
        this.institutionId = institutionId;
        this.date = date;
        this.month= month;
        this.year =year;
        this.grade = grade;
        this.section = section;
        this.name = name;
    }
}
