package com.institution.repository;

import com.institution.model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TeacherRepository extends MongoRepository<Teacher, Long> {
    List<Teacher> findTeacherByInstitutionId(long InstitutionId);
}
