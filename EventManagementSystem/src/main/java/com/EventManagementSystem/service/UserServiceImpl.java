package com.EventManagementSystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.EventManagementSystem.dto.UserRequestDTO;
import com.EventManagementSystem.dto.UserResponseDTO;
import com.EventManagementSystem.entity.Address;
import com.EventManagementSystem.entity.Roles;
import com.EventManagementSystem.entity.User;
import com.EventManagementSystem.repository.RoleRepository;
import com.EventManagementSystem.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {

		Roles role = roleRepository.findByRoleName(userRequestDTO.getRole())
				.orElseThrow(() -> new RuntimeException("role is not exists"));
		User user = new User();
		user.setUserName(userRequestDTO.getUserName());
		user.setEmail(userRequestDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
		user.setPhone(userRequestDTO.getPhone());
		user.setRole(role);

		if (userRequestDTO.getAddress() != null && !userRequestDTO.getAddress().isEmpty()) {

			List<Address> addressList = userRequestDTO.getAddress().stream().map(dto -> {
				Address address = new Address();
				address.setHouseNumber(dto.getHouseNumber());
				address.setStreet(dto.getStreet());
				address.setArea(dto.getArea());
				address.setCity(dto.getCity());
				address.setState(dto.getState());
				address.setPincode(dto.getPincode());
				address.setLatitude(dto.getLatitude());
				address.setLongitude(dto.getLongitude());
				return address;
			}).collect(Collectors.toList());

			user.setAddress(addressList);
		}

		user = userRepository.save(user);

		return modelMapper.map(user, UserResponseDTO.class);
	}

	public UserResponseDTO filterUser(Long uId) {
		User user = userRepository.findById(uId).orElseThrow(() -> new RuntimeException("user not found"));
		return modelMapper.map(user, UserResponseDTO.class);
	}

	public List<UserResponseDTO> allUser() {
		List<User> user = userRepository.findAll();
		return user.stream().map(dto -> modelMapper.map(dto, UserResponseDTO.class)).collect(Collectors.toList());
	}

	public UserResponseDTO updateUser(Long uId, UserRequestDTO userRequestDTO) {
		User userInDB = userRepository.findById(uId)
				.orElseThrow(() -> new RuntimeException("user not found with this id " + uId));
		userInDB.setUserName(userRequestDTO.getUserName());
		userInDB.setEmail(userRequestDTO.getEmail());
		userInDB.setPassword(userRequestDTO.getPassword());
		userInDB.setPhone(userRequestDTO.getPhone());

		User updatedUser = userRepository.save(userInDB);
		return modelMapper.map(updatedUser, UserResponseDTO.class);
	}

}
