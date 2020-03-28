package com.institution.service;
import java.util.Optional;
import java.util.Set;

import com.institution.model.CustomUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.institution.model.ApplicationUser;
import com.institution.repository.UserRepository;

import static java.util.Collections.emptyList;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private UserRepository repo;
	
	public UserDetailsServiceImpl(UserRepository repo) {
		this.repo = repo;
	}
    

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 CustomUser user = null;
    	 ApplicationUser applicationUser = repo.findByEmail(email);
    	 if (applicationUser == null) {
             throw new UsernameNotFoundException(email);
         }
    	 Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    	 grantedAuthorities.add(new SimpleGrantedAuthority(applicationUser.getRole().toString()));
    	 return new CustomUser(applicationUser.getEmail(), applicationUser.getPassword(),
				 grantedAuthorities, applicationUser.getInstitution());
    	 //return new org.springframework.security.core.userdetails.User(applicationUser.getEmail(), applicationUser.getPassword(),
				// grantedAuthorities);
    }

}
