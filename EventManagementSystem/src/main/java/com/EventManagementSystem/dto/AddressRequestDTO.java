package com.EventManagementSystem.dto;

import com.EventManagementSystem.entity.Zone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddressRequestDTO {
	private String houseNumber;
    private String street;
    private String city;      // selected from dropdown
    private String zoneName;  // selected from city-based zones
    private String state;
    private String pincode;
	

}
