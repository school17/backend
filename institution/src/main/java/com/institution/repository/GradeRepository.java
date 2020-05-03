package com.institution.repository;

import com.institution.model.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface GradeRepository extends MongoRepository<Grade, Long> {

    public List<Grade> findGradeByInstitutionId(String institutionId);

    public List<Grade> findGradeByInstitutionIdAndSection(String institutionId, String section);

    //"{ 'name' : { $regex: ?0 }, 'email' : { $regex: ?1}, 'institutionId' : ?2 } "
    @Query("{ 'grade' : { $regex: ?0 }, 'section' : { $regex: ?1} , 'teacher' : { $regex: ?2}, 'institutionId' : ?3 } ")
    Page<Grade> searchGrade(String grade, String section, String teacher, long institutionId, Pageable pageable);


    @Query("{ 'teacher' : { $regex: ?0 }, 'institutionId' : ?1 } ")
    Page<Grade> searchGradeByTeacher(String teacher, long institutionId, Pageable pageable);

    public Grade findGradeByInstitutionIdAndId(Long institutionId, Long id);

}
