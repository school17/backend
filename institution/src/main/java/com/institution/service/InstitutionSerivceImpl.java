package com.institution.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.institution.errorHandling.EntityNotFoundException;
import com.institution.model.Address;
import com.institution.model.Institution;
import com.institution.repository.InstitutionRepository;
import com.institution.service.SequenceGeneratorService;

@Service
public class InstitutionSerivceImpl implements InstitutionService {
	
	@Autowired
	InstitutionRepository repo;
	
	@Autowired
	SequenceGeneratorService sequenceGenerator;

	@Override
	public Institution createInstitution(Institution institution) {
		institution.setId(sequenceGenerator.generateSequence(Institution.SEQUENCE_NAME));
		return repo.save(institution);
	}

	public Institution updateInstitutionDetails(Institution institution, long institutionId) {
		Optional<Institution> getInstitution = repo.findById(institutionId);
		if(getInstitution.isEmpty()) {
			throw new EntityNotFoundException(Institution.class, "id", Long.toString(institutionId));
			
		}else {
			Institution updateInstitution = getInstitution.get();
			updateInstitution.setBranch(institution.getBranch());
			updateInstitution.setEmail(institution.getEmail());
			updateInstitution.setGrades(institution.getGrades());
			updateInstitution.setMode(institution.getMode());
			updateInstitution.setPhoneNumber(institution.getPhoneNumber());
			updateInstitution.setSchoolName(institution.getSchoolName());
			Address  address = new Address(institution.getAddress().getAddress1(), institution.getAddress().getAddress2(), 
					institution.getAddress().getArea(),institution.getAddress().getCity(), institution.getAddress().getPincode(), institution.getAddress().getStreet(),  institution.getAddress().getState());
			updateInstitution.setAddress(address);
			return repo.save(updateInstitution);
		}
		
	}

	@Override
	public List<Institution> findAllInstitution() {
		return repo.findAll();
	}

	@Override
	public Institution findInstitution(Long institutionId) {
		Optional<Institution> getInstitution = repo.findById(institutionId);
		if(getInstitution.isEmpty()){
			throw new EntityNotFoundException(Institution.class, "id", Long.toString(institutionId));
		}
		else {
			return getInstitution.get();
		}
	}

}
