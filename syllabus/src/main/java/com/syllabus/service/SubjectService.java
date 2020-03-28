package com.syllabus.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.syllabus.repo.Subject;

@Service
public interface SubjectService {
	
	public Optional<Subject> getSubject(Long id);
	public Subject createSubject(Subject subject);
	public List<Subject> getAllChapter(String mode, String state, String subject, String grade);
	public List<Subject> getAllNonMetriculationChapter(String mode, String subject, String grade);
	public Subject createSubjectWithTopics(Subject subject);
	public Optional<Subject> updateSubject(Subject subject, Long subjectId);
	public ResponseEntity<?> deleteSubject(Long subjectId);

}
