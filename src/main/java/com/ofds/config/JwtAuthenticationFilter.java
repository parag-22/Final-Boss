package com.ofds.config;



import java.io.IOException;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ofds.service.CustomerService;
import com.ofds.service.CustomerUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/*
 * 
 * The JwtAuthenticationFilter is a custom security filter that runs once per request 
 * and checks for a valid JWT token in the Authorization header of incoming HTTP 
 * requests. If a token is present and starts with "Bearer ", 
 * it extracts the token and retrieves the associated username using JwtUtils. 
 * It then loads the user's details from the database via CustomUserDetailsService 
 * and validates the token. If the token is valid and the user is not already 
 * authenticated, it creates an authentication object and sets it in the 
 * Spring Security context, effectively logging the user in for that request. 
 * Finally, it passes control to the next filter in the chain, allowing the request 
 * to proceed. This mechanism ensures that only authenticated users with valid 
 * tokens can access protected resources.
 * 
 * 
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	
	CustomerService userDetailsService;
	
	@Autowired
	CustomerUserDetailsService authCustService;
	


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
	 //From the header, get the data for the field Authorization.
		String authHeader = request.getHeader("Authorization");
		
	 String token = null;
	 String email = null;
	 
	 //If the Header is not null and it starts with Bearer.
	 if (authHeader != null && authHeader.startsWith("Bearer ")) {
		 
		 //Gets the token which is after the Bearer --> 7 characters. Bearer + space.
		 token = authHeader.substring(7);
		 
		 //Get the user name from the token.
		 email = jwtUtils.extractUsername(token);
	 }
	 
	 // Checks if a username is provided and no authentication is currently set in the security context.
	 if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		
		 // Loads user details from the database or service using the provided username.
		 UserDetails userDetails = authCustService.loadUserByUsername(email);

		 // Validates the JWT token against the retrieved user details.
		 if (jwtUtils.validateToken(token, userDetails)) {
			 
			// Creates an authentication token with user details and granted authorities.
			 UsernamePasswordAuthenticationToken authToken = 
					  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			 
			// Attaches additional request-specific details to the authentication token.
			 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			 
			 // Sets the authentication token in the security context to mark the user as authenticated.
			 SecurityContextHolder.getContext().setAuthentication(authToken);
 		 
		 }
		 
		 
	 }
	 //Forwards the request to the next chain of request, after authentication or not authentication.
	 //If Authenticate, moves the control to the next action or next request or the request which it came for.
	 // Else throws the exception.
	 filterChain.doFilter(request, response);
		
	} 
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	    String path = request.getServletPath();
	    return path.startsWith("/auth/login") || path.startsWith("/auth/register");
	}


}

