package com.EventManagementSystem.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignZonesRequestDTO {
	
	 private List<Long> zoneIds;

}
