package com.institution.service;

import com.institution.errorHandling.EntityNotFoundException;
import com.institution.model.ApplicationUser;
import com.institution.model.Institution;
import com.institution.model.Teacher;
import com.institution.repository.TeacherRepository;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public TeacherServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Autowired
    UserServiceDao userService;

    @Override
    public Teacher createTeacher(Teacher teacher) {
        InstitutionSerivceImpl institutionSerivce = new InstitutionSerivceImpl();
        ApplicationUser user = new ApplicationUser();
        user.setId(sequenceGenerator.generateSequence(ApplicationUser.SEQUENCE_NAME));
        user.setRole("TEACHER");
        user.setPassword(institutionSerivce.generatePassword());
        user.setEmail(teacher.getEmail());
        userService.createUser(user, Long.toString(teacher.getId()));

        teacher.setId(sequenceGenerator.generateSequence(Teacher.SEQUENCE_NAME));
        teacher.setApplicationUserId(user.getId());
        System.out.println("CLASS TEACHER " + teacher.getClassTeacher());
        String isClassTeacher = teacher.getClassTeacher() == null ? "false" : "true";
        teacher.setClassTeacher(Optional.ofNullable(teacher.getClassTeacher()).orElse("NOT ASSIGNED"));
        teacher.setClassTeacher(isClassTeacher);
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> getAllTeacher(long institutionId) {
        return teacherRepository.findTeacherByInstitutionId(institutionId);
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        System.out.println("ID" + teacher.getId());
        Optional<Teacher>  teacher1 = teacherRepository.findById(teacher.getId());
        if(teacher1 == null) {
            throw new EntityNotFoundException(Teacher.class, "id", Long.toString(teacher.getId()));
        }else {
            Teacher updateTeacher = teacher1.get();
            updateTeacher.setClassTeacher(teacher.getClassTeacher());
            updateTeacher.setAddress(teacher.getAddress());
            updateTeacher.setDivision(teacher.getDivision());
            updateTeacher.setEmail(teacher.getEmail());
            updateTeacher.setGrades(teacher.getGrades());
            updateTeacher.setSubjects(teacher.getSubjects());
            updateTeacher.setGrade(teacher.getGrade());
            updateTeacher.setPicture(teacher.getPicture());
            updateTeacher.setName(teacher.getName());
            return teacherRepository.save(updateTeacher);

        }

    }


    @Override
    public Page<Teacher> searchTeachers(Teacher teacher, long institutionId, Map<String,String> searchParam) {
        int pageNumber = Integer.parseInt(Optional.ofNullable(searchParam.get("pageNumber")).orElse("0"));
        int pageSize = Integer.parseInt(Optional.ofNullable(searchParam.get("?pageSize")).orElse("10"));
        Pageable page  =  PageRequest.of(pageNumber,pageSize);
        Set<String> subjects = new TreeSet<String>();
        Set<String> grades = new TreeSet<String>();
        if(!(teacher.getSubjects() == null) && !(teacher.getGrades() == null))
        {
            subjects.addAll(teacher.getSubjects());
            grades.addAll(teacher.getGrades());

            return teacherRepository.searchTeacherWithGradesAndGrades(Optional.ofNullable(teacher.getName()).orElse(""),
                    Optional.ofNullable(teacher.getEmail()).orElse("") ,
                    grades,
                    subjects,
                    institutionId, page);
        }

        if(!(teacher.getSubjects() == null)){
            subjects.addAll(teacher.getSubjects());

            return teacherRepository.searchTeacherWithSubjects(Optional.ofNullable(teacher.getName()).orElse(""),
                    Optional.ofNullable(teacher.getEmail()).orElse("") ,
                    subjects,
                    institutionId, page);
        }

        if(!(teacher.getGrades() == null)) {
            grades.addAll(teacher.getGrades());
            return teacherRepository.searchTeacherWithGrades(Optional.ofNullable(teacher.getName()).orElse(""),
                    Optional.ofNullable(teacher.getEmail()).orElse("") ,
                    grades,
                    institutionId, page);
        }

        else {
            return teacherRepository.searchTeacher(Optional.ofNullable(teacher.getName()).orElse(""),
                    Optional.ofNullable(teacher.getEmail()).orElse("") ,
                    institutionId, page);
        }
    }

    @Override
    public List<Teacher> findNonClassTeachers(long institutionId) {
        return teacherRepository.findAvailableTeachers(institutionId);
    }

    @Override
    public Optional<Teacher> findTeacherByGrade(Long institutionId, String grade, String name) {
       return  teacherRepository.findTeacherByInstitutionIdAndGradeAndName(institutionId, grade, name);
    }
}
