package com.EventManagementSystem.dto;

import java.util.List;

import com.EventManagementSystem.entity.Address;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
	private String userName;
	private String email;
	private String password;
	private String phone;
	@Enumerated(EnumType.STRING)
	private String role;
	private List<AddressRequestDTO> address;

}
