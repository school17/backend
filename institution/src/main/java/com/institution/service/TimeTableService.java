package com.institution.service;

import com.institution.model.grade.TimeTable;

public interface TimeTableService {
    public TimeTable saveTimeTable(TimeTable timeTable);

    public TimeTable getTimeTable(long institutionId, String grade, String section);

    public TimeTable updateTimeTable(long institutionId, String grade, String section, TimeTable timeTable, long id);
}
