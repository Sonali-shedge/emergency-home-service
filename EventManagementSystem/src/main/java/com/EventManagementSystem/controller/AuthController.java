package com.EventManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EventManagementSystem.dto.LoginRequestDTO;
import com.EventManagementSystem.dto.LoginResponseDTO;
import com.EventManagementSystem.dto.UserRequestDTO;
import com.EventManagementSystem.dto.UserResponseDTO;
import com.EventManagementSystem.service.AuthServiceImpl;
import com.EventManagementSystem.service.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthServiceImpl authServiceImpl;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@PostMapping("/registerUser")
	public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO) {
		return new ResponseEntity<UserResponseDTO>(userServiceImpl.registerUser(userRequestDTO), HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
		return new ResponseEntity<LoginResponseDTO>(authServiceImpl.login(loginRequestDTO), HttpStatus.OK);
	}

}
