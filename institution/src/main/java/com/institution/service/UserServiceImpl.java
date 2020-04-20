package com.institution.service;

import com.institution.errorHandling.EntityNotFoundException;
import com.institution.model.Institution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.institution.model.ApplicationUser;
import com.institution.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceDao {
	
	@Autowired
	UserRepository userRepo;

	@Autowired
	MailService emailService;

	@Autowired
	SequenceGeneratorService sequenceGenerator;
	@Override
	public void createUser(ApplicationUser applicationUser, String institutionId) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String tempPassword = applicationUser.getPassword();
		applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
		applicationUser.setRole(applicationUser.getRole());
		applicationUser.setTemporaryPassword(true);
		applicationUser.setInstitution(institutionId);
		applicationUser.setId(sequenceGenerator.generateSequence(ApplicationUser.SEQUENCE_NAME));
		userRepo.save(applicationUser);
		emailService.sendMail(applicationUser.getEmail(), "New User Creation", "Welcome you user name is " +
				applicationUser.getEmail() + " and temporary password is "+tempPassword);

	}

	public void updatePassword(ApplicationUser applicationUser) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		ApplicationUser user = userRepo.findApplicationUserByEmailAndInstitution(applicationUser.getEmail(),
				applicationUser.getInstitution());

		user.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
		user.setTemporaryPassword(false);
		userRepo.save(user);
	}

	public ApplicationUser getUser(String email, String institutionId) {
		ApplicationUser user =  userRepo.findApplicationUserByEmailAndInstitution(email, institutionId);
		if(user == null) {
			throw new EntityNotFoundException(ApplicationUser.class, "id", email);
		}else {
			return user;
		}

	}

}
