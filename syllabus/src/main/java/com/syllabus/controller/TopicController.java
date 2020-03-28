package com.syllabus.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.syllabus.repo.Topic;
import com.syllabus.service.SubjectService;
import com.syllabus.service.TopicService;

@CrossOrigin(origins = "*")
@RestController
public class TopicController {
	@Autowired
	private TopicService service;
	
	@Autowired
	private SubjectService subjectservice;
	
	@PostMapping("/syllabus/subject/{subjectId}/topic")
	public Optional<Object> saveTopic(@PathVariable (value = "subjectId") Long subjectId, @RequestBody List<Topic> topics) {
		return subjectservice.getSubject(subjectId).map(subject ->{
			for(Topic topic: topics) {
				topic.setSubject(subject);
				service.saveTopic(topic);
			}
			return topics;
		});
	}
	
	@DeleteMapping("/syllabus/subject/{subjectId}/topic/{topicId}")
	public ResponseEntity<?>  deleteTopic(@PathVariable Long subjectId, @PathVariable Long topicId){
		return service.deleteTopic(subjectId, topicId);
	}
}
