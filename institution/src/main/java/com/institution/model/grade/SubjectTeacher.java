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
import java.util.List;
import java.util.Map;

@Document(collection = "subjectteachers")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SubjectTeacher extends CustomAuditing implements Persistable<Long> {

    @Transient
    public static final String SEQUENCE_NAME = "application_subjectteacher_sequence";
    private boolean persisted;

    @NotNull
    private long institutionId;

    private long id;

    @NotNull
    private String division;

    @NotNull
    private String grade;

    @NotNull
    private String section;

    private List<Map<String, String> > subjectTeachers;



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
