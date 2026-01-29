package com.EventManagementSystem.service;

import java.util.List;

import org.jspecify.annotations.Nullable;

import com.EventManagementSystem.dto.AssignZonesRequestDTO;
import com.EventManagementSystem.dto.ZoneRequestDTO;
import com.EventManagementSystem.dto.ZoneResponseDTO;

public interface ZoneService {
	
	public  ZoneResponseDTO addZone(ZoneRequestDTO zoneRequestDTO);
	
	public @Nullable List<ZoneResponseDTO> getZoneByCity(String city);

	public String assignZonesToProvider(Long providerId, AssignZonesRequestDTO requestDTO); 
	
	public List<String> getAllCities();
}
