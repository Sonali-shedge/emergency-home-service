package com.EventManagementSystem.dto;

import com.EventManagementSystem.entity.User;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDTO {
	 private Long addressId;
	private String houseNumber;
    private String street;
    private String city;      // selected from dropdown
    private String zoneName;  // selected from city-based zones
    private String state;
    private String pincode;

}
