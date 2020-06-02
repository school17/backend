package com.institution.repository;

import com.institution.model.Test;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TestRepository  extends MongoRepository<Test, Long> {
    public List<Test> findTestByInstitutionIdAndDivisionAndGrade(Long institutionId, String section, String grade);

    public Optional<Test> findTestByInstitutionIdAndId(Long institutionId, Long id);
}
