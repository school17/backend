package com.institution.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.institution.errorHandling.EntityNotFoundException;
import com.institution.model.Institution;
import com.institution.repository.InstitutionRepository;
import com.institution.service.InstitutionService;

@RestController
@RequestMapping("/api/")
public class InstitutionController {
	Logger logger = LoggerFactory.getLogger(InstitutionController.class);
	@Autowired
	InstitutionService institutionSerivce;
	
	@PostMapping("institution/create_institution")
	public Institution createInstitution(@RequestBody Institution institution) {
		logger.info("Creating Institution");
		return institutionSerivce.createInstitution(institution);
	}

	@GetMapping("institution/{institutionId}/get_institution")
	public Institution getInstitution(@PathVariable(value = "institutionId") Long institutionId) {
		logger.info("Getting institution by id " + institutionId);
		return institutionSerivce.findInstitution(institutionId);
	}

	@PutMapping("institution/{institutionId}/update_institution")
	public Institution updateInstitutionDetails(@RequestBody Institution institution, @PathVariable(value = "institutionId") Long institutionId) throws EntityNotFoundException{
		return institutionSerivce.updateInstitutionDetails(institution, institutionId);
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN', 'TEACHER')")
	@GetMapping("/institutions")
	public List<Institution> findAllInstitution() {
		return institutionSerivce.findAllInstitution();
	}
}
