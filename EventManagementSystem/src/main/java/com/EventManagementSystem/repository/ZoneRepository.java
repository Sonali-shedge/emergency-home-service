package com.EventManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.EventManagementSystem.entity.Zone;


@Repository
public interface ZoneRepository  extends JpaRepository<Zone, Long>{
	
	Optional<Zone> findByCityIgnoreCaseAndZoneNameIgnoreCase(String city, String zoneName);
	
	
	List<Zone> findByCityIgnoreCaseAndActiveTrue(String city);
	
	List<Zone> findByZoneIdIn(List<Long> zoneIds);

	List<Zone> findByCityIgnoreCase(String city);
	
	@Query("SELECT DISTINCT z.city FROM Zone z WHERE z.active = true")
	List<String> findDistinctCities();
}
