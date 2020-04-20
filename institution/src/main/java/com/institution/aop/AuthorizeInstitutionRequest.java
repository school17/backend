package com.institution.aop;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.institution.security.SecurityConstants.*;

@Aspect
@Component
public class AuthorizeInstitutionRequest {
    @Autowired
    private HttpServletRequest request;

    @Before(value = "execution(* com.institution.controller.*.*(..)) && args(institutionId,..)")
    public void beforeAdvice(JoinPoint joinPoint, Object institutionId) {
      String token = request.getHeader(HEADER_STRING);
      if(token != null) {
          String institution =  JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                  .build()
                  .verify(token.replace(TOKEN_PREFIX, ""))
                  .getClaim("institution").asString();

          //if(institutionId != institution) {
           //   throw new AccessDeniedException("Access Denied: Failed");
         // }
      }
    }

}
