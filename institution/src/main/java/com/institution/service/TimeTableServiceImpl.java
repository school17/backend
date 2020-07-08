package com.institution.service;

import com.institution.errorHandling.EntityNotFoundException;
import com.institution.model.grade.SubjectTeacher;
import com.institution.model.grade.TimeTable;
import com.institution.repository.TimeTableReposiroty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TimeTableServiceImpl implements TimeTableService {

    @Autowired
    TimeTableReposiroty timeTableReposiroty;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Autowired
    TeacherService teacherService;

    @Override
    public TimeTable saveTimeTable(TimeTable timeTable) {
        timeTable.setId(sequenceGenerator.generateSequence(TimeTable.SEQUENCE_NAME));
        timeTable.setPersisted(false);
        teacherService.updateTeacherTimeTable(timeTable, timeTable.getInstitutionId(), timeTable.getGrade(), timeTable.getSection());
        return timeTableReposiroty.save(timeTable);
    }

    @Override
    public TimeTable getTimeTable(long institutionId, String grade, String section) {
        return timeTableReposiroty.findByInstitutionIdAndGradeAndSection(institutionId, grade, section);
    }

    @Override
    public TimeTable updateTimeTable(long institutionId, String grade, String section, TimeTable timeTable, long id) {
        TimeTable timeTable1 = timeTableReposiroty.findByInstitutionIdAndGradeAndSection(institutionId, grade, section);
        System.out.println("GETTING TIME TABLE " + timeTable1.toString());
        if(timeTable1 !=null) {
            timeTable1 = timeTable;
            timeTable1.setPersisted(true);
            timeTable1.setId(id);
        } else {
            throw new EntityNotFoundException(TimeTable.class, "id", Long.toString(timeTable.getId()));
        }
        return timeTableReposiroty.save(timeTable1);
    }
}
