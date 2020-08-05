package com.institution.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Document(collection = "recordofworks")
@Getter
@Setter
@NoArgsConstructor
public class RecordOfWork implements Persistable<Long> {

    @Transient
    public static final String SEQUENCE_NAME = "application_recordOfWork_sequence";
    private boolean persisted;

    private long id;
    @NotNull
    private long institutionId;

    @NotNull
    private  String grade;

    @NotNull
    private String Subject;

    @NotNull
    private String Division;

    private Set<Map<String, ArrayList<String>>> recordOfWorks;

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
