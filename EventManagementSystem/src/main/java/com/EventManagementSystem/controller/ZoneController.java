package com.EventManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EventManagementSystem.dto.AssignZonesRequestDTO;
import com.EventManagementSystem.dto.ZoneRequestDTO;
import com.EventManagementSystem.dto.ZoneResponseDTO;
import com.EventManagementSystem.service.ZoneServiceImpl;

@RestController
@RequestMapping("/api/zone")
public class ZoneController {

	@Autowired
	private ZoneServiceImpl zoneServiceImpl;

	@PostMapping("/addZone")
	public ResponseEntity<ZoneResponseDTO> addZone(@RequestBody ZoneRequestDTO zoneRequestDTO) {
		return new ResponseEntity<ZoneResponseDTO>(zoneServiceImpl.addZone(zoneRequestDTO), HttpStatus.CREATED);
	}

	@GetMapping("/getZoneByCity/{city}")
	public ResponseEntity<List<ZoneResponseDTO>> getZoneByCity(@PathVariable String city) {
		return new ResponseEntity<List<ZoneResponseDTO>>(zoneServiceImpl.getZoneByCity(city), HttpStatus.OK);
	}
	
	@GetMapping("/getAllCities")
	public ResponseEntity<List<String>> getAllCities() {
	    return ResponseEntity.ok(zoneServiceImpl.getAllCities());
	}

	@PostMapping("/{providerId}/zones")
	public ResponseEntity<String> assignZonesToProvider(@PathVariable Long providerId,
			@RequestBody AssignZonesRequestDTO requestDTO) {
		return new ResponseEntity<String>(zoneServiceImpl.assignZonesToProvider(providerId, requestDTO), HttpStatus.OK);
	}

}
