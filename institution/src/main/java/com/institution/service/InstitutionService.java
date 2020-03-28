package com.institution.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.institution.model.Institution;

@Service
public interface InstitutionService {
	
	public Institution createInstitution(Institution institution);
	
	public Institution updateInstitutionDetails(Institution institution, long institutionId);
	
	public List<Institution> findAllInstitution();

	public Institution findInstitution(Long institutionId);
}
