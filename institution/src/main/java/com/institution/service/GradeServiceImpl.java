package com.institution.service;

import com.institution.errorHandling.EntityNotFoundException;
import com.institution.model.Grade;
import com.institution.model.Institution;
import com.institution.model.Teacher;
import com.institution.model.grade.AvailableGradesAndSections;
import com.institution.model.grade.DivisionGrade;
import com.institution.model.grade.Section;
import com.institution.repository.GradeRepository;
import com.institution.repository.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    GradeRepository repo;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Autowired
    InstitutionService institutionService;

    Logger logger = LoggerFactory.getLogger(GradeServiceImpl.class);

    @Override
    public Grade createGrade(Grade grade) {
        grade.setId(sequenceGenerator.generateSequence(Grade.SEQUENCE_NAME));

        updateTeacher(grade);
        return repo.save(grade);
    }

    @Override
    public List<Grade> listAllGrades(String institutionId) {
        return repo.findGradeByInstitutionId(institutionId);
    }

    @Override
    public Page<Grade> searchGrades(Grade grade, long institutionId, Map<String, String> searchParam) {

        int pageNumber = Integer.parseInt(Optional.ofNullable(searchParam.get("pageNumber")).orElse("0"));
        int pageSize = Integer.parseInt(Optional.ofNullable(searchParam.get("?pageSize")).orElse("10"));
        Pageable page  =  PageRequest.of(pageNumber,pageSize);
        return repo.searchGrade(Optional.ofNullable(grade.getGrade()).orElse(""),
                Optional.ofNullable(grade.getSection()).orElse(""),
                Optional.ofNullable(grade.getTeacher()).orElse(""),
                institutionId,page);
    }

    @Override
    public Grade updateGrade(Grade grade, Long institutionId, Long id) {
        Grade getGrade = repo.findGradeByInstitutionIdAndId(institutionId, id);
        if(getGrade != null) {
            if(grade.isUpdateTeacher()) {
                Optional<Teacher> teacher = teacherRepository.findTeacherByInstitutionIdAndId(grade.getInstitutionId()
                        , grade.getPreviousTeacherId());
                if(teacher == null) {
                    throw new EntityNotFoundException(Teacher.class, "id", Long.toString(grade.getTeacherId()));
                }else {
                    Teacher teach = teacher.get();
                    teach.setGrade("");
                    teach.setClassTeacher("false");
                    teach.setPersisted(true);
                    teacherRepository.save(teach);

                }
            }

            logger.info("UPDATING GRADE MODEL");
            getGrade.setDivision(grade.getDivision());
            getGrade.setSection(grade.getSection());
            getGrade.setTeacher(grade.getTeacher());
            updateTeacher(grade);
            getGrade.setGrade(grade.getGrade());
            getGrade.setTeacherId(grade.getTeacherId());
        }
        return repo.save(getGrade);
    }

    @Override
    public Grade findGradeByInstitutionIdAndGradeAndSection(Long institutionId, String grade, String section) {
        return repo.findGradeByInstitutionIdAndGradeAndSection(institutionId, grade, section);
    }

    public void updateTeacher(Grade grade) {
        logger.info("UPDATING TEACHER WHILE GRADE OPERATION");
        Optional<Teacher> teacher = teacherRepository.findTeacherByInstitutionIdAndId(grade.getInstitutionId()
                , grade.getTeacherId());
        if(teacher == null) {
            throw new EntityNotFoundException(Teacher.class, "id", Long.toString(grade.getTeacherId()));
        }else {
            Teacher teach = teacher.get();
            teach.setGrade(grade.getGrade() + " " + grade.getSection());
            teach.setClassTeacher("true");
            teach.setPersisted(true);
            teacherRepository.save(teach);

        }
    }
}
