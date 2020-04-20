package com.institution.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "users")
@Getter @Setter @NoArgsConstructor @ToString
public class ApplicationUser extends CustomAuditing {

	@Transient
	public static final String SEQUENCE_NAME = "application_users_sequence";

	@Id
	private long id;
	//@Indexed(name = "email_index", direction = IndexDirection.DESCENDING)
	private String email;
	private String password;
	private String username;
	private String role;
	private String passwordExpiry;
	private boolean isTemporaryPassword;
	private String institution;
	

}
