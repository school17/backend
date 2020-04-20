package com.institution.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static com.institution.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

	 @Override
	    protected void doFilterInternal(HttpServletRequest req,
	                                    HttpServletResponse res,
	                                    FilterChain chain
										) throws IOException, ServletException {

		 String header = req.getHeader(HEADER_STRING);

	        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
	            chain.doFilter(req, res);
	            return;
	        }

	        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        chain.doFilter(req, res);
	    }

	    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
	        String token = request.getHeader(HEADER_STRING);

	        if (token != null) {
	            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
	                    .build()
	                    .verify(token.replace(TOKEN_PREFIX, ""))
	                    .getSubject();
	            
	            String role =  JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
	                    .build()
	                    .verify(token.replace(TOKEN_PREFIX, ""))
	                    .getClaim("role").asString();
	            
	            if (user != null) {
	                return new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority(role)));
	            }
	            return null;
	        }
	        return null;
	    }
}
