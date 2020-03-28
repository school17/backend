package com.institution.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.institution.model.ApplicationUser;
import com.institution.repository.UserRepository;
import com.institution.service.UserDetailsServiceImpl;
import com.institution.service.UserServiceDao;

@RestController
public class UserController {
	
	@Autowired
	UserServiceDao service;
	
	private UserRepository repo;
	
	@Autowired UserDetailsServiceImpl serviced;
	
	public UserController(UserRepository repo) {
		this.repo = repo;
	}
	
	@PostMapping("/users/sign-up")
	public long registerUser(@RequestBody ApplicationUser applicationUser) {
		service.createUser(applicationUser);
		return repo.count();
		
	}
	
	@GetMapping("/users/demo")
	public String demo() {
		return serviced.loadUserByUsername("goutham").toString();
	}
	

}
