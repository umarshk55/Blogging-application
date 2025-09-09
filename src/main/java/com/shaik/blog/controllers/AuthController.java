package com.shaik.blog.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shaik.blog.entities.User;
import com.shaik.blog.exceptions.ApiException;
import com.shaik.blog.payloads.JwtAuthRequest;
import com.shaik.blog.payloads.JwtAuthResponse;
import com.shaik.blog.payloads.UserDto;
import com.shaik.blog.security.JwtTokenHelper;
import com.shaik.blog.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

	@Autowired
	private JwtTokenHelper jetTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper mapper;

	
	@Operation(summary = "Login user", description = "Authenticate user and return JWT token")
	@PostMapping("login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest request) throws Exception 
	{
		this.authenticate(request.getUsername(),request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jetTokenHelper.generateToken(userDetails);
		
		
		
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		
		response.setUser(this.mapper.map((User) userDetails, UserDto.class));
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}
	
	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details!!");
			throw new ApiException("Inavlid username or password !!");
		}
		
	}
	
	@Operation(summary = "Register user", description = "Register a new user with roles")
	//register new user api
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto usetrDto)
	{
		UserDto registeredUser = this.userService.registerNewUser(usetrDto);
		
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
	}
		
}
