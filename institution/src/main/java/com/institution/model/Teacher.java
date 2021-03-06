package com.institution.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Document(collection = "teachers")
@Getter @Setter @NoArgsConstructor
@ToString
// implements Persistable<Long>
public class Teacher extends CustomAuditing  implements Persistable<Long> {
    @Transient
    public static final String SEQUENCE_NAME = "application_teachers_sequence";

    private long id;
    private long applicationUserId;

    private Set<String> subjects;
    private Set<String> grades;
    private Set<String> division;
    private String classTeacher;
    private String grade;
    private String email;
    private Address address;
    private long institutionId;
    private String name;
    private String picture;
    private boolean persisted;

    private ArrayList<Map<String, ArrayList<String>>> timeTable;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return !persisted;
    }
}
