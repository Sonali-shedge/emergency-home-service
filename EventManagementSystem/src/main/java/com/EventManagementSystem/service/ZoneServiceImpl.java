package com.EventManagementSystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.EventManagementSystem.dto.AssignZonesRequestDTO;
import com.EventManagementSystem.dto.ZoneRequestDTO;
import com.EventManagementSystem.dto.ZoneResponseDTO;
import com.EventManagementSystem.entity.ServiceProvider;
import com.EventManagementSystem.entity.Zone;
import com.EventManagementSystem.repository.ServiceProviderRepository;
import com.EventManagementSystem.repository.ZoneRepository;



@Service
public class ZoneServiceImpl implements ZoneService {
	
	@Autowired
	private ZoneRepository zoneRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ServiceProviderRepository serviceProviderRepository;

	@Override
	public ZoneResponseDTO addZone(ZoneRequestDTO zoneRequestDTO) {
		
		String city = zoneRequestDTO.getCity().trim();
		String zoneName= zoneRequestDTO.getZoneName().trim();
		zoneRepository.findByCityIgnoreCaseAndZoneNameIgnoreCase(city, zoneName).ifPresent(dto->
		{
			throw new RuntimeException("Zone already exists: " + city + " - " + zoneName);
		});
		
		Zone zone = new Zone();
		zone.setCity(city);
		zone.setZoneName(zoneName);
		zone.setActive(true);
		Zone savedZone = zoneRepository.save(zone);
		
		return modelMapper.map(savedZone, ZoneResponseDTO.class);
	}

	@Override
	public @Nullable List<ZoneResponseDTO> getZoneByCity(String city) {
		List<Zone> zones = zoneRepository.findByCityIgnoreCaseAndActiveTrue(city.trim());
		return zones.stream().map(dto-> modelMapper.map(dto, ZoneResponseDTO.class)).collect(Collectors.toList());
	}

	@Override
	public String assignZonesToProvider(Long providerId, AssignZonesRequestDTO requestDTO) {
	ServiceProvider serviceProvider =	serviceProviderRepository.findById(providerId).orElseThrow(()-> new RuntimeException("Provider not found"));
	List<Long> zoneIds =requestDTO.getZoneIds();
	List <Zone> zones =  zoneRepository.findByZoneIdIn(zoneIds);
	if(zones.isEmpty())
	{
		throw new RuntimeException("no valid zones are found");
	}
serviceProvider.setServiceZones(zones);
serviceProviderRepository.save(serviceProvider);
		return "Zones assigned successfully";
	}

	@Override
	public List<String> getAllCities() {
	    return zoneRepository.findDistinctCities();
	}
	
	
	



	

	
}
