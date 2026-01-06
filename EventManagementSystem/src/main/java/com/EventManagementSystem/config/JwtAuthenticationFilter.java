package com.EventManagementSystem.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.EventManagementSystem.service.CustomUserDetailsService;
import com.EventManagementSystem.utils.JwtUtils;
import org.slf4j.LoggerFactory;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	//private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
    	System.out.println("JWT FILTER CALLED FOR URI: " + request.getRequestURI());
    	System.out.println("AUTH HEADER: " + request.getHeader("Authorization"));

    	
    	 String path = request.getServletPath();

         // 🚨 SKIP JWT CHECK FOR PUBLIC APIs
         if (path.startsWith("/login") ||
             path.startsWith("/registerUser") ||
             path.startsWith("/api/auth")
             ) {

             filterChain.doFilter(request, response);
             return;
         }


        final String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;
    
        // 1️⃣ Check Authorization header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            System.out.println("AUTH HEADER: " + request.getHeader("Authorization"));

        }

        // 2️⃣ Validate token & set security context
        if (username != null &&
            SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);
            System.out.println("USERNAME FROM TOKEN: " + username);
            System.out.println("AUTHORITIES FROM DB: " + userDetails.getAuthorities());


            if (jwtUtil.isTokenValid(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
            }
        }

        // 3️⃣ Continue filter chain
        filterChain.doFilter(request, response);
    }
    
 

}

