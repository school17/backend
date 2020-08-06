package com.institution.repository;

import com.institution.model.RecordOfWork;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Repository
public interface RecordOfWorkRepository extends MongoRepository<RecordOfWork,Long>
{
    RecordOfWork findRecordOfWorkByInstitutionIdAndGradeAndDivisionAndSubject(long institutionId, String grade,String division,String subject);

}
