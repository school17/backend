package com.institution.service;

import com.institution.model.Student;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.Map;

public interface StudentService {

    public Student createStudent(Student student, long institutionId);

    public Page<Student> searchStudent(Student student, long institutionId, Map<String,String> searchParam);

    public Student updateStudent(Student student, long institutionId, long studentId);
}
