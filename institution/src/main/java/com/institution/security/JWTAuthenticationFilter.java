package com.institution.security;

import com.institution.model.CustomUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.institution.model.ApplicationUser;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.institution.security.SecurityConstants.*;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;


//public class JWTAuthenticationFilter {}
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	 private AuthenticationManager authenticationManager;
	 
	 public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
	        this.authenticationManager = authenticationManager;
	 }
	 
	 @Override
	    public Authentication attemptAuthentication(HttpServletRequest req,
	                                                HttpServletResponse res) throws AuthenticationException {
	        try {
	        	ApplicationUser creds = new ObjectMapper()
	                    .readValue(req.getInputStream(), ApplicationUser.class);
	            return authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(
	                    		creds.getEmail(),
	                            creds.getPassword(),
								Arrays.asList())
	            );
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    @Override
	    protected void successfulAuthentication(HttpServletRequest req,
	                                            HttpServletResponse res,
	                                            FilterChain chain,
	                                            Authentication auth) throws IOException, ServletException {
	    	List<GrantedAuthority> authorities
			= new ArrayList<>();
	    	for(GrantedAuthority authority : auth.getAuthorities()) {
	    		authorities.add(authority);
	    	}
	    	Date expiryDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
	    	CustomUser user = (CustomUser) auth.getPrincipal();
			String token = JWT.create()
	        		.withSubject(((CustomUser) auth.getPrincipal()).getUsername())
	                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .withClaim("role", authorities.get(0).toString())
					.withClaim("expiry", expiryDate.toString())
					.withClaim("institution", user.getInstitutionId())
	                .sign(HMAC512(SECRET.getBytes()));
	        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().write(
					"{\"" + SecurityConstants.HEADER_STRING + "\":\"" + SecurityConstants.TOKEN_PREFIX+token + "\"}"
			);

	    }

}
