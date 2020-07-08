package com.institution.service;

import com.institution.model.Teacher;
import com.institution.model.grade.TimeTable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface TeacherService {

    public Teacher createTeacher(Teacher teacher);

    public List<Teacher> getAllTeacher(long institutionId);

    public Teacher updateTeacher(Teacher teacher, Long id);

    public Page<Teacher> searchTeachers(Teacher teacher, long institutionId, Map<String,String> searchParam);

    public List<Teacher> findNonClassTeachers(long institutionId);

    public Optional<Teacher> findTeacherByGrade(Long institutionId,String grade, String name);

    public void deleteTeacher(long institutionId, long id);

    public Teacher getTeacherDetails(long institutionId, String email);

    public void updateTeacherTimeTable(TimeTable timeTable, long institutionId, String grade, String section);

    public void updateTeacherTimeTableOnDelete(long institutionId, String name, String day, String period);
}
