package com.EventManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EventManagementSystem.entity.ServiceCategory;
import com.EventManagementSystem.entity.Services;

@Repository
public interface ServiceRepository  extends JpaRepository<Services, Long>{
	
	 List<Services> findByServiceCategory (ServiceCategory serviceCategory);

}
