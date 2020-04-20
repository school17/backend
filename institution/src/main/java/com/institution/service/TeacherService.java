package com.institution.service;

import com.institution.model.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherService {

    public Teacher createTeacher(Teacher teacher);

    public List<Teacher> getAllTeacher(long institutionId);

    public Teacher updateTeacher(Teacher teacher);
}
