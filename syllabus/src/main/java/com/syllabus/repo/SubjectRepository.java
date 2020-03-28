package com.syllabus.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
	List<Subject> findByModeAndSubjectAndStateAndGrade(String mode, String subject, String state, String grade);
	List<Subject> findByModeAndSubjectAndGrade(String mode, String subject, String grade);

}
