package com.institution.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Address extends CustomAuditing {
	
	private String address1;
	private String address2;
	private String street;
	private String area;
	private String city;
	private String pincode;
	private String state;
	
	public Address() {
		
	}
	

}
