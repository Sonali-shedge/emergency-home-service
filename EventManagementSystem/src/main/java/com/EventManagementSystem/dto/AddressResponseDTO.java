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
	private String area;
	private String city;
	private String state;
	private String pincode;
	private Double latitude;
	private Double longitude;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

}
