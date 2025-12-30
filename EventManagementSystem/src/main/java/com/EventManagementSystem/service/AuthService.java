package com.EventManagementSystem.service;

import com.EventManagementSystem.dto.LoginRequestDTO;
import com.EventManagementSystem.dto.LoginResponseDTO;

public interface AuthService {

	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

}
