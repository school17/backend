package com.institution.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.institution.model.ApplicationUser;
import com.institution.repository.UserRepository;

@Service
public class UserServiceImpl implements UserServiceDao {
	
	@Autowired
	UserRepository userRepo;

	@Override
	public void createUser(ApplicationUser applicationUser) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
		applicationUser.setRole(applicationUser.getRole());
		applicationUser.setTemporaryPassword(true);
		userRepo.save(applicationUser);

	}

}
