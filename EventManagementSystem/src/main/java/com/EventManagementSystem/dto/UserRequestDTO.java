package com.EventManagementSystem.dto;

import java.util.List;

import com.EventManagementSystem.enumT.Role;

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
//	@Enumerated(EnumType.STRING)
	private Role role;
	private List<AddressRequestDTO> address;

}
