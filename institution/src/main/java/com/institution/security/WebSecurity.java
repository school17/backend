package com.institution.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.institution.service.UserDetailsServiceImpl;

import static com.institution.security.SecurityConstants.*;

import java.util.Arrays;



//public class WebSecurity{}
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) 
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public WebSecurity(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
   
    
    protected void configure(HttpSecurity http) throws Exception{
    	http.cors().and().csrf().disable()
    	.antMatcher("/**")
    	.authorizeRequests()
    	.antMatchers(SIGN_UP_URL).permitAll()
    		.antMatchers("/api/**").authenticated()
    		.and()
    	.addFilter(new JWTAuthenticationFilter(authenticationManager()))
        .addFilter(new JWTAuthorizationFilter(authenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	
    	
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

  @Bean
    CorsConfigurationSource corsConfigurationSource() {
    /*final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;*/
      final CorsConfiguration configuration = new CorsConfiguration();
      configuration.setAllowedOrigins(Arrays.asList("*"));
      configuration.setAllowedMethods(Arrays.asList("HEAD",
              "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
      configuration.setAllowCredentials(true);
      configuration.setAllowedHeaders(Arrays.asList("*"));
      configuration.setExposedHeaders(Arrays.asList("X-Auth-Token","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials"));
      final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return source;
  }

}
