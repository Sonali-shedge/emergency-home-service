package com.EventManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EventManagementSystem.entity.ServiceCategory;
import com.EventManagementSystem.entity.ServiceProvider;
import com.EventManagementSystem.enumT.AvailabilityStatus;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
	
	List<ServiceProvider> findByServiceCategory_ServiceCategoryIdAndAvailabilityStatus(Long serviceCategoryId , AvailabilityStatus availabilityStatus);

}
