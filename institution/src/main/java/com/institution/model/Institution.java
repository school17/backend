package com.institution.model;

import java.util.Set;

import com.institution.model.grade.AvailableGradesAndSections;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "institutions")
@Getter @Setter @NoArgsConstructor
@ToString
public class Institution extends CustomAuditing {

	@Transient
    public static final String SEQUENCE_NAME = "institutions_sequence";
    @Id
    private long id;
    private String branch;
    private String schoolName;
    private String mode;
    private String grades;
    private String email;
    private String adminUser;
    private String phoneNumber;
    private Address address;
    private Set<String> divisionProvided;
    private Set<AvailableGradesAndSections> availableGradesAndSections;
    private boolean onboardingComplete;


}
