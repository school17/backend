package com.institution.service;

import com.institution.errorHandling.EntityNotFoundException;
import com.institution.model.RecordOfWork;
import com.institution.repository.RecordOfWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordOfWorkServiceImpl implements RecordOfWorkService {

    @Autowired
    RecordOfWorkRepository recordOfWorkRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Override
    public RecordOfWork findRecordOfWorks(long institutionId, String grade, String division, String subject) {
        return recordOfWorkRepository.recordOfWorkInstitutionIdANdGradeAndDivisionAndSubject(institutionId,grade,division,subject);
    }

    @Override
    public RecordOfWork saveRecordOfWork(RecordOfWork recordofwork) {
        RecordOfWork recordOfWorkRepo=recordOfWorkRepository.recordOfWorkInstitutionIdANdGradeAndDivisionAndSubject(recordofwork.getInstitutionId(),recordofwork.getGrade(),recordofwork.getDivision(),recordofwork.getSubject());

        if(recordOfWorkRepo==null)
        {
            recordofwork.setInstitutionId(sequenceGenerator.generateSequence(RecordOfWork.SEQUENCE_NAME));
            recordofwork.setPersisted(false);
            return recordOfWorkRepository.save(recordofwork);
        }
        else
        {
            RecordOfWork recordOfWork1  = recordOfWorkRepo;
            recordofwork.setPersisted(true);
            recordofwork.setId(recordOfWork1.getId());
            recordOfWork1 = recordofwork;
            return  recordOfWorkRepository.save(recordOfWork1);
        }
    }

    @Override
    public RecordOfWork updateRecordOfWork(RecordOfWork recordofwork) {
        RecordOfWork recordOfWorkRepo=recordOfWorkRepository.recordOfWorkInstitutionIdANdGradeAndDivisionAndSubject(recordofwork.getInstitutionId(),recordofwork.getGrade(),recordofwork.getDivision(),recordofwork.getSubject());
        if(recordOfWorkRepo !=null) {
            RecordOfWork recordOfWork1  = recordOfWorkRepo;
            recordofwork.setPersisted(true);
            recordofwork.setId(recordOfWork1.getId());
            recordOfWork1 = recordofwork;
            return  recordOfWorkRepository.save(recordOfWork1);
        } else {
            throw new EntityNotFoundException(RecordOfWork.class, "id", Long.toString(recordOfWorkRepo.getId()));
        }
    }
}
