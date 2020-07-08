package com.institution.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.institution.errorHandling.EntityNotFoundException;
import com.institution.messageSystem.MessageSender;
import com.institution.model.ApplicationUser;
import com.institution.model.Grade;
import com.institution.model.Student;
import com.institution.repository.GradeRepository;
import com.institution.repository.StudentsRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final ImageService imageService;

    StudentServiceImpl(ImageService imageService){
        this.imageService = imageService;
    }

    @Autowired
    StudentsRepository studentsRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Autowired
    UserServiceDao userService;

    @Autowired
    GradeService gradeService;

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    MessageSender messageSender;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public Student createStudent(Student student, long institutionId) {
        InstitutionSerivceImpl institutionSerivce = new InstitutionSerivceImpl();
        ApplicationUser user = new ApplicationUser();
        user.setId(sequenceGenerator.generateSequence(ApplicationUser.SEQUENCE_NAME));
        user.setRole("STUDENT");
        user.setPassword(institutionSerivce.generatePassword());
        user.setEmail(student.getEmail());
        user.setInstitution(institutionId);
        student.setId(sequenceGenerator.generateSequence(Student.SEQUENCE_NAME));
        student.setApplicationUserId(user.getId());
        student.setInstitutionId(institutionId);
        userService.createUser(user, institutionId);
       Grade grade = gradeService.findGradeByInstitutionIdAndGradeAndSection(student.getInstitutionId(), student.getGrade(), student.getSection());
        if(grade !=null) {
            grade.setStrength(grade.getStrength() + 1);
            gradeRepository.save(grade);
        }
        if(student.getPicture()!=null) {
            //student.setPicture(imageService.uploadFile(student.getPicture(), student.getName()));
        }
        return studentsRepository.save(student);
    }

    @Override
    public Page<Student> searchStudent(Student student, long institutionId, Map<String, String> searchParam) {
        int pageNumber = Integer.parseInt(Optional.ofNullable(searchParam.get("pageNumber")).orElse("0"));
        int pageSize = Integer.parseInt(Optional.ofNullable(searchParam.get("?pageSize")).orElse("10"));
        Pageable page  =  PageRequest.of(pageNumber,pageSize);

        //messageSender.sendMessage("dem");

        return studentsRepository.searchStudent(Optional.ofNullable(student.getName()).orElse(""),
                Optional.ofNullable(student.getEmail()).orElse(""),
                Optional.ofNullable(student.getGrade()).orElse(""),
                Optional.ofNullable(student.getSection()).orElse(""),
                institutionId, page);
    }

    @Override
    public Student updateStudent(Student student, long institutionId, long studentId) {
        Grade grade = null;
        Student updatedStudent = null;
        Optional<Student>  student1 = studentsRepository.findByInstitutionIdAndId(institutionId, student.getId());
        if(student1 == null) {
            throw new EntityNotFoundException(Student.class, "id", Long.toString(student.getId()));
        }else {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectReader objectReader = objectMapper.readerForUpdating(student1.get());
                try {
                   updatedStudent= objectReader.readValue(objectMapper.writeValueAsString(student));
                }catch (Exception e) {
                    e.printStackTrace();
                }

                if(student.isChangeGrade()) {
                    grade = gradeService.findGradeByInstitutionIdAndGradeAndSection(institutionId, student.getOldGrade(), student.getOldSection());
                    if(grade != null){
                        grade.setStrength(grade.getStrength() - 1);
                    }

                    grade = gradeService.findGradeByInstitutionIdAndGradeAndSection(institutionId, student.getGrade(), student.getSection());
                    if(grade != null){
                        grade.setStrength(grade.getStrength() + 1);
                    }
                }

                return studentsRepository.save(updatedStudent);
            }

        }

    @Override
    public List<Student> getStudentByGradeAndSection(long institutionId, String grade, String section) {
        Comparator<Student> compareByName = (Student o1, Student o2) -> o1.getName().compareTo( o2.getName() );
        List<Student> studentList = studentsRepository.findByInstitutionIdAndGradeAndSection(institutionId,grade,section);
        Collections.sort(studentList, compareByName);
        return studentList;
    }

    @Override
    public Student getStudentDetails(long institutionId, String email) {
        return studentsRepository.findByInstitutionIdAndEmail(institutionId, email);
    }
}
