package com.institution.service;

import com.institution.errorHandling.EntityNotFoundException;
import com.institution.model.grade.SubjectTeacher;
import com.institution.repository.SubjectTeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectTeacherServiceImpl implements SubjectTeacherService {

    @Autowired
    SubjectTeacherRepository subjectTeacherRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Override
    public SubjectTeacher saveSubjectTeacherAssociation(SubjectTeacher subjectTeacher) {
        subjectTeacher.setId(sequenceGenerator.generateSequence(SubjectTeacher.SEQUENCE_NAME));
        subjectTeacher.setPersisted(false);
        return subjectTeacherRepository.save(subjectTeacher);
    }

    @Override
    public SubjectTeacher findSubjectTeacherAssociation(long institutionId, String grade, String section) {
        return subjectTeacherRepository.findOneSubjectTeacherByInstitutionIdAndGradeAndSection(institutionId, grade, section);
    }

    @Override
    public SubjectTeacher updateSubjectTeacherAssociation(SubjectTeacher subjectTeacher, long institutionId, String grade, String section, long id) {
        Optional<SubjectTeacher> subjectTeacherOptional= subjectTeacherRepository.findById(id);

        if(subjectTeacherOptional.get() != null) {
            SubjectTeacher subjectTeacherSaved = subjectTeacherOptional.get();
            subjectTeacherSaved = subjectTeacher;
            subjectTeacherSaved.setPersisted(true);
            subjectTeacherSaved.setId(id);
            return subjectTeacherRepository.save(subjectTeacherSaved);
        } else {
            throw new EntityNotFoundException(SubjectTeacher.class, "id", Long.toString(subjectTeacher.getId()));
        }


    }


}
