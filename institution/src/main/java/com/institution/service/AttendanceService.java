package com.institution.service;

import com.institution.model.Attendance;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AttendanceService {

    public Attendance saveAttendance(Attendance attendance);

    public List<Attendance> findAttendance(long institutionId, String grade, String section, String Month, String year);
}
