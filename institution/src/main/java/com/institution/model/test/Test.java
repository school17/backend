package com.institution.model.test;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Document(collection = "tests")
@Getter
@Setter
@NoArgsConstructor
public class Test {

    @Transient
    public static final String SEQUENCE_NAME = "application_test_sequence";
    private long id;

    @NotNull
    private long institutionId;

    @NotEmpty
    private String testName;

    private String startDate;

    @NotEmpty
    private String division;

    @NotEmpty
    private String grade;

    private Set<Schedule> schedule;
}
