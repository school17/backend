package com.syllabus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.syllabus.repo.Subject;
import com.syllabus.repo.SubjectRepository;
import com.syllabus.repo.Topic;
@Service
public class SubjectServiceImpl implements SubjectService {
	
	@Autowired
	SubjectRepository repo;

	@Override
	public Optional<Subject> getSubject(Long id) {
		Optional<Subject> fetchedSubject = repo.findById(id);
		//Set<Topic> topics = fetchedSubject.get().gettopics();

		return fetchedSubject;
	}

	@Override
	public Subject createSubject(Subject subject) {
		return repo.save(subject);
	}

	@Override
	public List<Subject> getAllChapter(String mode, String state, String subject, String grade) {
		return repo.findByModeAndSubjectAndStateAndGrade(mode, subject, state, grade);
	}

	@Override
	public List<Subject> getAllNonMetriculationChapter(String mode, String subject, String grade) {
		return repo.findByModeAndSubjectAndGrade(mode, subject, grade);
	}

	@Override
	public Subject createSubjectWithTopics(Subject subject) {
		ArrayList<Topic> topicsArray = new ArrayList<Topic>();
		Subject newSubject = new Subject(subject.getState(), subject.getMode(),
				 subject.getGrade(), subject.getSubject(),subject.getChapterNumber(), subject.getChapterName());
		
		for(Topic topic: subject.gettopics()) {
			topicsArray.add(new Topic(topic.getTopic()));
		}
		for(Topic topic: topicsArray) {
			topic.setSubject(newSubject);
			newSubject.gettopics().add(topic);
		}
			return repo.save(newSubject);
	}

	@Override
	public Optional<Subject> updateSubject(Subject subject, Long subjectId) {
		return repo.findById(subjectId).map(updateSubject ->{
			updateSubject.setChapterName(subject.getChapterName());
			updateSubject.setChapterNumber(subject.getChapterNumber());
			return repo.save(updateSubject);
		});
	}

	@Override
	public ResponseEntity<?> deleteSubject(Long subjectId) {
		    Optional<Subject> subject = repo.findById(subjectId);
		    if(subject.isPresent()) {
		    	repo.deleteById(subjectId);
		    }
		    return ResponseEntity.ok().build();	
	}
	 

}
