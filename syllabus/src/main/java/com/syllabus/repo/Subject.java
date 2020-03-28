package com.syllabus.repo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "subjects")
public class Subject extends AuditModel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Size(max = 100)
	private String state;
    
    @NotNull
    @Size(max = 100)
    private String mode;
    
    @NotNull
    @Size(max = 3)
    private String grade;
    
    @NotNull
    @Size(max = 100)
    private String subject;
    
    @NotNull
    @Size(max = 2)
    private String chapterNumber;
    
    @NotNull
    @Size(max = 100)
    private String chapterName;
    
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "subject")
    private Set<Topic> topics = new HashSet<>();

	public Set<Topic> gettopics() {
		return topics;
	}

	public void settopics(Set<Topic> topics) {
		this.topics = topics;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getChapterNumber() {
		return chapterNumber;
	}

	public void setChapterNumber(String chapterNumber) {
		this.chapterNumber = chapterNumber;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public Subject(@Size(max = 100) String state, @NotNull @Size(max = 100) String mode,
			@NotNull @Size(max = 3) String grade, @NotNull @Size(max = 100) String subject,
			@NotNull @Size(max = 2) String chapterNumber, @NotNull @Size(max = 100) String chapterName) {
		super();
		this.state = state;
		this.mode = mode;
		this.grade = grade;
		this.subject = subject;
		this.chapterNumber = chapterNumber;
		this.chapterName = chapterName;
	}
	
	public Subject() {
		
	}
	
	
}
