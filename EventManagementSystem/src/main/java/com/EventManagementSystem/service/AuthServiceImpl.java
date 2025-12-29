package com.EventManagementSystem.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.EventManagementSystem.dto.LoginRequestDTO;
import com.EventManagementSystem.dto.LoginResponseDTO;
import com.EventManagementSystem.entity.User;
import com.EventManagementSystem.exception.InvalidCredentialsException;
import com.EventManagementSystem.exception.UserNotFoundException;
import com.EventManagementSystem.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

		User user = userRepository.findByEmail(loginRequestDTO.getEmail())
				.orElseThrow(() -> new UserNotFoundException("User is not present in DB"));

		if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
			throw new InvalidCredentialsException("invalid password..");
		}

		LoginResponseDTO dto = new LoginResponseDTO();
		dto.setUserId(user.getUserId());
		dto.setUserName(user.getUserName());
		dto.setEmail(user.getEmail());
		dto.setRole(user.getRole().getRoleName());
		dto.setMessage("Login successfull....!!!");

		return dto;
	}

}
