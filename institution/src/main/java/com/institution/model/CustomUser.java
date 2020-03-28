package com.institution.model;

import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {
    private static final long serialVersionUID = -3531439484732724601L;

    private String institutionId;

    public CustomUser(String username, String password, Collection authorities, String institutionId){
        super(username, password, authorities);
        this.institutionId = institutionId;

    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }


}
