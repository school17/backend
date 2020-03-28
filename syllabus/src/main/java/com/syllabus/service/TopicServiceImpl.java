package com.syllabus.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.syllabus.repo.Subject;
import com.syllabus.repo.Topic;
import com.syllabus.repo.TopicRepository;
@Service
public class TopicServiceImpl implements TopicService {
	
	@Autowired
	TopicRepository repo;

	@Override
	public List<Topic> getAllTopics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Topic saveTopic(Topic topic) {
		return repo.save(topic);
	}

	@Override
	public ResponseEntity<?> deleteTopic(Long subjectId, Long topicId) {
		Optional<Topic> topic = repo.findById(topicId);
	    if(topic.isPresent()) {
	    	repo.deleteById(topicId);
	    }
	    return ResponseEntity.ok().build();	
	}

}
