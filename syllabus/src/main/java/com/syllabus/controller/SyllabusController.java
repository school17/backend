package com.syllabus.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syllabus.repo.Subject;
import com.syllabus.service.SubjectService;

@CrossOrigin(origins = "*")
@RestController
public class SyllabusController {
	
	@Autowired
	SubjectService service;
	
	@GetMapping("/syllabus/subject/{subjectId}")
	public Optional<Subject> getSubject(@PathVariable Long subjectId) {
		return service.getSubject(subjectId);
		
	}
	
	@PostMapping("/syllabus/subject")
	public Subject saveSubject(@Valid @RequestBody Subject subject) {
		return service.createSubject(subject);
	}
	
	@GetMapping("/syllabus/{mode}/{subject}/{grade}/{state}")
	public List<Subject> findAllChapter(@PathVariable String mode, @PathVariable String state, 
			@PathVariable String subject, @PathVariable String grade) {
		return service.getAllChapter(mode,state,subject, grade);
	}
	
	@GetMapping("/syllabus/{mode}/{subject}/{grade}")
	public List<Subject> findAllChapter(@PathVariable String mode,
			@PathVariable String subject, @PathVariable String grade) {
		return service.getAllNonMetriculationChapter(mode,subject, grade);
	}
	
	@PostMapping("/syllabus/subject/new")
	public Subject saveNewSubject(@Valid @RequestBody Subject subject) {
		return service.createSubject(subject);
	}
	
	@PutMapping("/syllabus/subject/{subjectId}")
	public Optional<Subject>  updateSubject(@PathVariable Long subjectId, @Valid @RequestBody Subject subject) {
		return service.updateSubject(subject, subjectId);
		
	}
	
	@DeleteMapping("/syllabus/subject/{subjectId}")
	public ResponseEntity<?> deleteSubject(@PathVariable Long subjectId){
		return service.deleteSubject(subjectId);
	}

}
