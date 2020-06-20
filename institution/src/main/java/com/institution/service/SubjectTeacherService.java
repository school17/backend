package com.institution.service;

import com.institution.model.grade.SubjectTeacher;

public interface SubjectTeacherService {

    public SubjectTeacher saveSubjectTeacherAssociation(SubjectTeacher SubjectTeacher);

    public SubjectTeacher findSubjectTeacherAssociation(long institutionId, String grade, String section);

    public SubjectTeacher updateSubjectTeacherAssociation(SubjectTeacher SubjectTeacher, long institutionId, String grade, String section, long id);
}
