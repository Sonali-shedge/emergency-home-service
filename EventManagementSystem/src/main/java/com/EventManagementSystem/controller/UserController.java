package com.EventManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EventManagementSystem.dto.UserRequestDTO;
import com.EventManagementSystem.dto.UserResponseDTO;
import com.EventManagementSystem.service.UserServiceImpl;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@PostMapping("/registerUser")
	public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO) {
		return new ResponseEntity<UserResponseDTO>(userServiceImpl.registerUser(userRequestDTO), HttpStatus.CREATED);

	}

	@GetMapping("/filterUser/{uId}")
	public ResponseEntity<UserResponseDTO> filterUser(@PathVariable Long uId) {
		return new ResponseEntity<UserResponseDTO>(userServiceImpl.filterUser(uId), HttpStatus.OK);
	}

	@GetMapping("/allUser")
	public ResponseEntity<List<UserResponseDTO>> allUser() {
		return new ResponseEntity<List<UserResponseDTO>>(userServiceImpl.allUser(), HttpStatus.OK);
	}

	@PutMapping("/updateUser/{uId}")

	public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long uId,
			@RequestBody UserRequestDTO userRequestDTO) {
		return new ResponseEntity<UserResponseDTO>(userServiceImpl.updateUser(uId, userRequestDTO), HttpStatus.OK);
	}

}
