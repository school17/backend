package com.institution.repository;

import com.institution.model.Grade;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GradeRepository extends MongoRepository<Grade, Long> {

    public List<Grade> findGradeByInstitutionId(String institutionId);

    public List<Grade> findGradeByInstitutionIdAndSection(String institutionId, String section);
}
