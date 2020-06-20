package com.institution.repository;

import com.institution.model.grade.SubjectTeacher;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubjectTeacherRepository extends MongoRepository<SubjectTeacher, Long> {
    SubjectTeacher findOneSubjectTeacherByInstitutionIdAndGradeAndSection(long institutionId, String grade, String section);
}
