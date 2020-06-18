package com.institution.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.institution.errorHandling.EntityNotFoundException;
import com.institution.messageSystem.MessageSender;
import com.institution.model.ApplicationUser;
import com.institution.model.Grade;
import com.institution.model.Teacher;
import com.institution.repository.GradeRepository;
import com.institution.repository.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final MongoTemplate mongoTemplate;

    private final ImageService imageService;

    Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Autowired
    public TeacherServiceImpl(MongoTemplate mongoTemplate, ImageService imageService) {
        this.mongoTemplate = mongoTemplate;
        this.imageService = imageService;
    }
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Autowired
    UserServiceDao userService;

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    MessageSender messageSender;

    @Override
    public Teacher createTeacher(Teacher teacher) {
        InstitutionSerivceImpl institutionSerivce = new InstitutionSerivceImpl();
        ApplicationUser user = new ApplicationUser();
        user.setId(sequenceGenerator.generateSequence(ApplicationUser.SEQUENCE_NAME));
        user.setRole("TEACHER");
        user.setPassword(institutionSerivce.generatePassword());
        user.setEmail(teacher.getEmail());
        teacher.setId(sequenceGenerator.generateSequence(Teacher.SEQUENCE_NAME));
        userService.createUser(user, teacher.getInstitutionId());
        teacher.setApplicationUserId(user.getId());
        String isClassTeacher = teacher.getClassTeacher() == null ? "false" : "true";
        teacher.setClassTeacher(Optional.ofNullable(teacher.getClassTeacher()).orElse("NOT ASSIGNED"));
        teacher.setClassTeacher(isClassTeacher);
        if(teacher.getPicture()!=null) {
            //imageService.uploadFile(teacher.getPicture(), teacher.getName());
        }
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> getAllTeacher(long institutionId) {
        return teacherRepository.findTeacherByInstitutionId(institutionId);
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        Optional<Teacher>  teacher1 = teacherRepository.findById(teacher.getId());
        Teacher updateTeacher = null;
        if(teacher1 == null) {
            throw new EntityNotFoundException(Teacher.class, "id", Long.toString(teacher.getId()));
        }else {
            teacher.setClassTeacher(teacher1.get().getClassTeacher());
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectReader objectReader = objectMapper.readerForUpdating(teacher1.get());
            try {
                updateTeacher= objectReader.readValue(objectMapper.writeValueAsString(teacher));
            }catch (Exception e) {
                e.printStackTrace();
            }
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

    @Override
    public void deleteTeacher(long institutionId, long id) {
        Optional<Teacher> teacher = teacherRepository.findTeacherByInstitutionIdAndId(institutionId,id);
        if(teacher == null) {
            throw new EntityNotFoundException(Teacher.class, "id", Long.toString(id));
        }else {
            logger.info("Checking the teacher is a class teacher " + teacher.get().getClassTeacher());
            if(Boolean.parseBoolean(teacher.get().getClassTeacher())== true) {
                logger.info("Finding the class of the class teacher");
                Grade grade = gradeRepository.findGradeByInstitutionIdAndTeacherId(institutionId, id);
                grade.setTeacher("");
                gradeRepository.save(grade);
            }
            teacherRepository.deleteByInstitutionIdAndId(institutionId, id);
        }
    }

    @Override
    public Teacher getTeacherDetails(long institutionId, String email) {
        return teacherRepository.findTeacherByInstitutionIdAndEmail(institutionId, email);
    }
}
