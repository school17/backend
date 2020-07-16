package com.institution.repository;

import com.institution.model.HomeWork;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeWorkRepository  extends MongoRepository<HomeWork, Long> {

    HomeWork findOneHomeWorkByInstitutionIdAndGradeAndSectionAndDate(long institutionId, String grade, String section, String date);
}
