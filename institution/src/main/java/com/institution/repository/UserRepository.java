package com.institution.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.institution.model.ApplicationUser;

@Repository
public interface UserRepository extends MongoRepository<ApplicationUser, String> {
	ApplicationUser findByUsername(String username);
	ApplicationUser findByEmail(String email);
}
