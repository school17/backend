package com.institution.service;

import com.institution.model.HomeWork;

import java.util.Date;
import java.util.Optional;

public interface HomeWorkService {

    public HomeWork findHomeWorkForADay(long institutionId, String grade, String section, String date);

    public HomeWork saveHomeWork(HomeWork homework);

    public  HomeWork  updateHomeWork(HomeWork homework);
}
