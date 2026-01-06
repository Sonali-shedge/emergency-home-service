package com.EventManagementSystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	 @Autowired
	    private JwtAuthenticationFilter jwtAuthenticationFilter;
	 @Autowired
	 private JwtAuthEntryPoint jwtAuthEntryPoint;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
 

	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	        http.csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(auth -> auth
	            		.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
	                .requestMatchers("/api/auth/**").permitAll()
	                .requestMatchers("/login/**").permitAll()
	                .requestMatchers("/api/user/registerUser**").permitAll()
	                .requestMatchers("/api/service/**").hasAnyRole("ADMIN" , "USER")
	                .requestMatchers("/api/user/allUser/").hasAuthority("ROLE_ADMIN")
	                .requestMatchers("/api/service//assignProvider/{serviceRequestId}").hasAnyRole("ADMIN" , "USER")
	                .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
	                .anyRequest().authenticated()
	            )
	            .sessionManagement(session ->
	                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            )
	            .addFilterBefore(jwtAuthenticationFilter,
	                    UsernamePasswordAuthenticationFilter.class);
	        http.exceptionHandling(ex ->
	        ex.authenticationEntryPoint(jwtAuthEntryPoint)
	);

	        return http.build();
	    }
	    
	    @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**")
	                        .allowedOrigins("http://localhost:5173")
	                        .allowedMethods("GET", "POST", "PUT", "DELETE")
	                        .allowedHeaders("*");
	            }
	        };
	    }

	}


