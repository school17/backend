package com.institution.controller;

import com.institution.model.Institution;
import com.institution.service.InstitutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateInstitutionController {

    Logger logger = LoggerFactory.getLogger(InstitutionController.class);
    @Autowired
    InstitutionService institutionSerivce;

    @PostMapping("/institution/create_institution")
    public Institution createInstitution(@RequestBody Institution institution) {
        logger.info("Creating Institution " + institution.toString());
        return institutionSerivce.createInstitution(institution);
    }
}
