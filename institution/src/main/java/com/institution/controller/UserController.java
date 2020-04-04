package com.institution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.institution.model.ApplicationUser;
import com.institution.repository.UserRepository;
import com.institution.service.UserServiceDao;

@RestController
public class UserController {
	
	@Autowired
	UserServiceDao service;
	
	private UserRepository repo;
	
	//@Autowired UserDetailsServiceImpl serviced;
	
	public UserController(UserRepository repo) {
		this.repo = repo;
	}
	
	@PostMapping("/users/sign-up")
	public long registerUser(@RequestBody ApplicationUser applicationUser) {
		service.createUser(applicationUser, "17");
		return repo.count();
		
	}
	
	@GetMapping("/users/demo")
	public ApplicationUser demo() {
		//return serviced.loadUserByUsername("goutham").toString();
		return repo.findApplicationUserByEmailAndInstitution("gouthamnavlal@gmail.com", "42");
	}

	@PutMapping("/api/users/updatePassword")
	public void updatePassword(@RequestBody ApplicationUser applicationUser){
		service.updatePassword(applicationUser);
		//return repo.findApplicationUserByEmailAndInstitution(applicationUser.getEmail(), applicationUser.getInstitution());
	}

	@GetMapping("/api/institution/{institutionId}/users/{userName}")
	public ApplicationUser getUserDetails(@PathVariable(value = "institutionId") String institutionId, @PathVariable(value = "userName") String  userName) {
		return service.getUser(userName, institutionId);
	}
	

}
