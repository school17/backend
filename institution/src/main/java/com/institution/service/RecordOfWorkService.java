package com.institution.service;

import com.institution.model.RecordOfWork;

public interface RecordOfWorkService {
    public RecordOfWork findRecordOfWorks(long institutionId,String grade,String division,String subject);

    public RecordOfWork saveRecordOfWork(RecordOfWork recordofwork);

    public RecordOfWork updateRecordOfWork(RecordOfWork recordofwork);


}
