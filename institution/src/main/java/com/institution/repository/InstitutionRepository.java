package com.institution.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.institution.model.Institution;

public interface InstitutionRepository extends MongoRepository<Institution, Long> {

}
