package com.institution.repository;

import com.institution.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TeacherRepository extends MongoRepository<Teacher, Long> {
    List<Teacher> findTeacherByInstitutionId(long InstitutionId);

    @Query("{ 'name' : { $regex: ?0 }, 'email' : { $regex: ?1}, 'institutionId' : ?2 } ")
    Page<Teacher> searchTeacher(String name, String email, long institutionId, Pageable pageable);

    @Query("{ 'name' : { $regex: ?0 }, 'email' : { $regex: ?1} , 'subjects' : { $all: ?2 }, 'institutionId' : ?3 } ")
    Page<Teacher> searchTeacherWithSubjects(String name, String email, Set<String> subjects, long institutionId, Pageable pageable);

    @Query("{ 'name' : { $regex: ?0 }, 'email' : { $regex: ?1} , 'grades' : { $all: ?2 }, 'institutionId' : ?3 } ")
    Page<Teacher> searchTeacherWithGrades(String name, String email, Set<String> grades, long institutionId, Pageable pageable);

    @Query("{ 'name' : { $regex: ?0 }, 'email' : { $regex: ?1} , 'grades' : { $all: ?2 }, 'subjects' : { $all: ?3 }, 'institutionId' : ?4 } ")
    Page<Teacher> searchTeacherWithGradesAndGrades(String name, String email, Set<String> grades, Set<String> subjects, long institutionId, Pageable pageable);

    @Query("{'classTeacher' : 'false', 'institutionId' : ?0}")
    List<Teacher> findAvailableTeachers(long institutionId);

    Optional<Teacher> findTeacherByInstitutionIdAndId(long InstitutionId, long id);

    Optional<Teacher> findTeacherByInstitutionIdAndGradeAndName(Long institutionId, String grade, String name);

}
