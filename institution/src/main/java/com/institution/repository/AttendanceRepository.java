package com.institution.repository;

import com.institution.model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AttendanceRepository extends MongoRepository<Attendance, Long> {

    List<Attendance> findAttendanceByInstitutionIdAndGradeAndSectionAndMonthAndYear(long institutionId, String grade, String section, String Month, String year);
}
