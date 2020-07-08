package com.institution.model.grade;


import com.institution.model.CustomAuditing;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Map;

@Document(collection = "timetable")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TimeTable extends CustomAuditing implements Persistable<Long> {


    @Transient
    public static final String SEQUENCE_NAME = "application_timetable_sequence";
    private boolean persisted;

    @NotNull
    private long institutionId;

    private long id;

    @NotNull
    private String grade;

    @NotNull
    private String section;

    private ArrayList<Map<String, ArrayList<Map<String, String>>>> timetable;

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
