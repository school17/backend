package com.syllabus.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
	Page<Topic> findBySubjectId(Long subjectId, Pageable pageable);
	Optional<Topic> findByIdAndSubjectId(Long id, Long subjectId);
}
