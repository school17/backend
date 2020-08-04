package com.institution.service;

import com.institution.model.Attendance;
import com.institution.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AttendanceServiceImpl implements  AttendanceService{

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Override
    public Attendance saveAttendance(Attendance attendance) {
        attendance.setId(sequenceGenerator.generateSequence(Attendance.SEQUENCE_NAME));
        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> findAttendance(long institutionId, String grade, String section, String Month, String year) {
        return attendanceRepository.findAttendanceByInstitutionIdAndGradeAndSectionAndMonthAndYear(institutionId,
                grade, section, Month, year);
    }

    @Override
    public void saveBulkAttendance(Attendance attendance) {
        if(attendance.getStudentsList().size() > 0) {
            for(String name: attendance.getStudentsList())
            saveAttendance(new Attendance(attendance.getInstitutionId(), attendance.getDate(),
                    attendance.getMonth(),attendance.getYear(), attendance.getGrade(),
                    attendance.getSection(), name));
        }
    }
}
