package com.institution.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

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

    @NonNull
    private String name;

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
}
