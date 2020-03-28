package com.syllabus.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.syllabus.repo.Topic;
@Service
public interface TopicService {
	public List<Topic> getAllTopics();
	public Topic saveTopic(Topic topic);
	public ResponseEntity<?> deleteTopic(Long subjectId, Long topicId);
}
