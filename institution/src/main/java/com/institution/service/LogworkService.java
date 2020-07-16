package com.institution.service;

import com.institution.model.Logwork;

public interface LogworkService {

    public Logwork getLogworkForADay(long institutionId, String grade, String section, String date);

    public Logwork saveLogWork(Logwork logwork);
}
