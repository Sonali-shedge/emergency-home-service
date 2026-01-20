package com.EventManagementSystem.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EventManagementSystem.dto.AddressResponseDTO;
import com.EventManagementSystem.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<AddressResponseDTO> getUserAddresses(String email) {
	return 	addressRepository.findByUserEmail(email).stream().map(dto-> modelMapper.map(dto, AddressResponseDTO.class)).toList();
		
		
	}

	 
}
