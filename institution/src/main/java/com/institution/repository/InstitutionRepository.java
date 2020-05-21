package com.institution.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.institution.model.Institution;
import org.springframework.data.mongodb.repository.Query;

public interface InstitutionRepository extends MongoRepository<Institution, Long> {

}
