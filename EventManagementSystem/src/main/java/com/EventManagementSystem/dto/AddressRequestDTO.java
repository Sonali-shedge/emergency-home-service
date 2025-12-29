package com.EventManagementSystem.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddressRequestDTO {
	private String houseNumber;
	private String street;
	private String area;
	private String city;
	private String state;
	private String pincode;
	private Double latitude;
	private Double longitude;

}
