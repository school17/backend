package com.institution.service;

import com.institution.model.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface TeacherService {

    public Teacher createTeacher(Teacher teacher);

    public List<Teacher> getAllTeacher(long institutionId);

    public Teacher updateTeacher(Teacher teacher);

    public Page<Teacher> searchTeachers(Teacher teacher, long institutionId, Map<String,String> searchParam);

    public List<Teacher> findNonClassTeachers(long institutionId);

    public Optional<Teacher> findTeacherByGrade(Long institutionId,String grade, String name);
}
