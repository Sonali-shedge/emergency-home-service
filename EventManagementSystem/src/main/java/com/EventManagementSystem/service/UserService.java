package com.EventManagementSystem.service;

import java.util.List;

import com.EventManagementSystem.dto.UserRequestDTO;
import com.EventManagementSystem.dto.UserResponseDTO;

public interface UserService {

	public UserResponseDTO registerUser(UserRequestDTO userRequestDTO);

	public UserResponseDTO filterUser(Long uId);

	public List<UserResponseDTO> allUser();

	public UserResponseDTO updateUser(Long uId, UserRequestDTO userRequestDTO);

}
