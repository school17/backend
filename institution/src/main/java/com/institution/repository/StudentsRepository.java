package com.institution.repository;

import com.institution.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface StudentsRepository extends MongoRepository<Student, Long> {

    @Query("{ 'name' : { $regex: ?0 }, 'email' : { $regex: ?1}, 'grade' : { $regex: ?2}, 'section' : { $regex: ?3}," +
            " 'institutionId' : ?4 } ")
    Page<Student>  searchStudent(String name, String email, String grade, String section, long institutionId, Pageable pageable);

    Optional<Student> findByInstitutionIdAndId(long institutionId, long id);
}
