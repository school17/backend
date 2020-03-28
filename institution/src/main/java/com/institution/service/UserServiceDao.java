package com.institution.service;

import org.springframework.stereotype.Service;

import com.institution.model.ApplicationUser;

@Service
public interface UserServiceDao {
	
	public void createUser(ApplicationUser applicationUser);

}
