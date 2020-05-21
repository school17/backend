package com.institution.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "students")
@Getter
@Setter
@NoArgsConstructor
//implements Persistable<Long>
public class Student extends CustomAuditing  {

    @Transient
    public static final String SEQUENCE_NAME = "application_students_sequence";
    //private boolean persisted;

    private long id;
    private long applicationUserId;

    @NonNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String grade;

    private String section;

    @NotNull
    private String division;

    @NotNull
    private long institutionId;

    private String dob;

    private String bloodGroup;

    private String picture;

    private String pictureStatus;

    private Address address;

    @NotNull
    private String phone;

    @NotNull
    private  String gender;

    @Transient
    private boolean changeGrade;

    @Transient
    private String oldGrade;

    @Transient
    private String oldSection;


    /*@Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return !persisted;
    }*/
}
