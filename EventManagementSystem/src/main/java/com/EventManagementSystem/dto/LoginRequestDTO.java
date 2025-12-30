package com.EventManagementSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
	@NotBlank(message = "email cannot be empty..")
	@Email(message = "invalid email format")
	private String email;
	@NotBlank(message = "password cannot be empty")
	private String password;

}
