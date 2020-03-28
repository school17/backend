package com.institution.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "users")
@Getter @Setter @NoArgsConstructor @ToString
public class ApplicationUser { 
	@Id
	private String id;
	private String email;
	private String password;
	private String username;
	private String role;
	private String passwordExpiry;
	private boolean isTemporaryPassword;
	private String institution;
	

}
