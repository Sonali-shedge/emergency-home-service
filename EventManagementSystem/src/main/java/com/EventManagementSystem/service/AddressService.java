package com.EventManagementSystem.service;

import java.util.List;

import com.EventManagementSystem.dto.AddressResponseDTO;

public interface AddressService {
	
	public List<AddressResponseDTO> getUserAddresses(String email);

}
