package com.institution.service;

import java.util.*;
import java.util.stream.Collectors;

import com.institution.controller.InstitutionController;
import com.institution.model.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.institution.errorHandling.EntityNotFoundException;
import com.institution.repository.InstitutionRepository;
import com.institution.service.SequenceGeneratorService;

@Service
public class InstitutionSerivceImpl implements InstitutionService {
	Logger logger = LoggerFactory.getLogger(InstitutionSerivceImpl.class);
	@Autowired
	InstitutionRepository repo;
	
	@Autowired
	SequenceGeneratorService sequenceGenerator;

	@Autowired
	UserServiceDao userService;


	@Override
	public Institution createInstitution(Institution institution) {
		institution.setId(sequenceGenerator.generateSequence(Institution.SEQUENCE_NAME));
		logger.info("creating a user " + generatePassword());
		ApplicationUser user = new ApplicationUser();
		user.setId(sequenceGenerator.generateSequence(ApplicationUser.SEQUENCE_NAME));
		user.setRole("ADMIN");
		logger.info("password generation " + generatePassword());
		user.setPassword(generatePassword());
		user.setEmail(institution.getAdminUser());
		userService.createUser(user, Long.toString(institution.getId()));
		return repo.save(institution);
	}

	public Institution updateInstitutionDetails(Institution institution, long institutionId) {
		String sectionArray[] = {"A", "B","C","D","E","F","G","H","I","J"};
		Optional<Institution> getInstitution = repo.findById(institutionId);
		if(getInstitution == null) {
			throw new EntityNotFoundException(Institution.class, "id", Long.toString(institutionId));
			
		}else {
			Institution updateInstitution = getInstitution.get();
			institution.setId(institutionId);
			/*updateInstitution.setBranch(institution.getBranch());
			updateInstitution.setEmail(institution.getEmail());
			updateInstitution.setGrades(institution.getGrades());
			updateInstitution.setMode(institution.getMode());
			updateInstitution.setPhoneNumber(institution.getPhoneNumber());
			updateInstitution.setSchoolName(institution.getSchoolName());
			Address  address = new Address(institution.getAddress().getAddress1(), institution.getAddress().getAddress2(), 
					institution.getAddress().getArea(),institution.getAddress().getCity(), institution.getAddress().getPincode(), institution.getAddress().getStreet(),  institution.getAddress().getState());
			updateInstitution.setAddress(address);*/

			if(institution.getDivisionProvided()!=null){
				Set<AvailableGradesAndSections> availableGradesAndSections = institution.getAvailableGradesAndSections();
				Set<AvailableGradesAndSections> newAvailableGradesAndSections = new HashSet<AvailableGradesAndSections>();
				for(AvailableGradesAndSections availableGradesAndSectionsItr : availableGradesAndSections) {
					AvailableGradesAndSections availableGradesAndSectionsObj = new AvailableGradesAndSections();
					availableGradesAndSectionsObj.setDivision(availableGradesAndSectionsItr.getDivision());
					Set<DivisionGrade> divisionGradeSet = availableGradesAndSectionsItr.getDivisionGrade();
					Set<DivisionGrade> newDivisionGradeSet = new HashSet<DivisionGrade>();
					for(DivisionGrade divisionGradeItr: divisionGradeSet) {
						DivisionGrade divisionGradeObj = new DivisionGrade();
						divisionGradeObj.setGrade(divisionGradeItr.getGrade());
						Set<Section> sectionSet = new HashSet<>();
						for(int i=0; i < Integer.parseInt(divisionGradeItr.getNumberOfSections()); i++) {

							Section sec = new Section(sectionArray[i], false);
							System.out.println(sec.toString());
							sectionSet.add(sec);
						}
						divisionGradeObj.setSection(sectionSet);
						newDivisionGradeSet.add(divisionGradeObj);
					}

					availableGradesAndSectionsObj.setDivisionGrade(newDivisionGradeSet);
					newAvailableGradesAndSections.add(availableGradesAndSectionsObj);
				}
				institution.setAvailableGradesAndSections(newAvailableGradesAndSections);
			}
			institution.setAdminUser(updateInstitution.getAdminUser());
			institution.setSchoolName(updateInstitution.getSchoolName());
			institution.setOnboardingComplete(true);

			return repo.save(institution);
		}
		
	}

	@Override
	public List<Institution> findAllInstitution() {
		return repo.findAll();
	}

	@Override
	public Institution findInstitution(Long institutionId) {
		Optional<Institution> getInstitution = repo.findById(institutionId);
		if(getInstitution == null){
			throw new EntityNotFoundException(Institution.class, "id", Long.toString(institutionId));
		}
		else {
			return getInstitution.get();
		}
	}

	public String generatePassword() {
		String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
		String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
		String numbers = RandomStringUtils.randomNumeric(2);
		String specialChar = RandomStringUtils.random(1, 33, 47, false, false);
		String totalChars = RandomStringUtils.randomAlphanumeric(2);
		String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
				.concat(numbers)
				.concat(specialChar)
				.concat(totalChars);
		List<Character> pwdChars = combinedChars.chars()
				.mapToObj(c -> (char) c)
				.collect(Collectors.toList());
		Collections.shuffle(pwdChars);
		String password = pwdChars.stream()
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
				.toString();
		return password;
	}

}

/*
AvailableGradesAndSections newGradesAndSections = new AvailableGradesAndSections();
					newGradesAndSections.setDivision(GradesAndSections.getDivision());

					Set<DivisionGrade> divisionGrade = GradesAndSections.getDivisionGrade();
					Set<DivisionGrade> newDivisionGradeSet = new TreeSet<>();
					for(DivisionGrade divisionGradeItr : divisionGrade) {
						Set<Section> sectionSet = divisionGradeItr.getSection();
						DivisionGrade newDivisionGrade = new DivisionGrade();
						newDivisionGrade.setGrade(divisionGradeItr.getGrade());



						newDivisionGradeSet.add(newDivisionGrade);
					}
					newGradesAndSections.setDivisionGrade(newDivisionGradeSet);
					newAvailableGradesAndSections.add(newGradesAndSections);
 */
