package com.institution.service;

import com.institution.errorHandling.EntityNotFoundException;
import com.institution.model.ApplicationUser;
import com.institution.model.Institution;
import com.institution.model.Teacher;
import com.institution.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

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
        teacher.setClassTeacher(teacher.getClassTeacher());
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> getAllTeacher(long institutionId) {
        return teacherRepository.findTeacherByInstitutionId(institutionId);
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
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
            return teacherRepository.save(updateTeacher);

        }

    }
}
