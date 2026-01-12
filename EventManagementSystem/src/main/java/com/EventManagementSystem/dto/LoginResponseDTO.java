package com.EventManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
	private Long userId;
	private String userName;
	private String email;
	private String role;
	private String message;
	private String token;

}
