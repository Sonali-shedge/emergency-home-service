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
import com.EventManagementSystem.service.AuthServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthServiceImpl authServiceImpl;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
		return new ResponseEntity<LoginResponseDTO>(authServiceImpl.login(loginRequestDTO), HttpStatus.OK);
	}

}
