package com.institution.service;


import com.institution.errorHandling.EntityNotFoundException;
import com.institution.model.HomeWork;
import com.institution.repository.HomeWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class HomeWorkServiceImpl implements  HomeWorkService {

    @Autowired
    HomeWorkRepository homeWorkRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;


    @Override
    public HomeWork findHomeWorkForADay(long institutionId, String grade, String section, String date) {
        return homeWorkRepository.findOneHomeWorkByInstitutionIdAndGradeAndSectionAndDate(institutionId, grade, section, date);
    }

    @Override
    public HomeWork saveHomeWork(HomeWork homework) {
        HomeWork homeworkrepo = homeWorkRepository.findOneHomeWorkByInstitutionIdAndGradeAndSectionAndDate(homework.getInstitutionId(),
                homework.getGrade(), homework.getSection(), homework.getDate());

        if(homeworkrepo == null) {
            homework.setId(sequenceGenerator.generateSequence(HomeWork.SEQUENCE_NAME));
            homework.setPersisted(false);
            return homeWorkRepository.save(homework);

        } else {
            HomeWork homeWork1  = homeworkrepo;
            homework.setPersisted(true);
            homework.setId(homeWork1.getId());
            homeWork1 = homework;
            return  homeWorkRepository.save(homeWork1);
        }
    }

    @Override
    public  HomeWork  updateHomeWork(HomeWork homework) {
        HomeWork optionalHomeWork = homeWorkRepository.findOneHomeWorkByInstitutionIdAndGradeAndSectionAndDate(homework.getInstitutionId(),
                homework.getGrade(),
                homework.getSection(),
                homework.getDate());

        if(optionalHomeWork !=null) {
            HomeWork homeWork1  = optionalHomeWork;
            homework.setPersisted(true);
            homeWork1 = homework;
            return  homeWorkRepository.save(homeWork1);

        } else {
            throw new EntityNotFoundException(HomeWork.class, "id", Long.toString(optionalHomeWork.getId()));
        }
    }
}
