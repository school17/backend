package com.institution.repository;

import com.institution.model.Logwork;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LogworkRepository  extends MongoRepository<Logwork, Long> {

    public Logwork findLogworkByInstitutionIdAndGradeAndSectionAndDate(long institutionId, String grade, String section, String date);
}
