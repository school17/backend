package com.institution.repository;

import com.institution.model.grade.TimeTable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimeTableReposiroty extends MongoRepository<TimeTable, Long> {
    public TimeTable findByInstitutionIdAndGradeAndSection(long institutionId, String grade, String section);
}
