package com.EventManagementSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EventManagementSystem.entity.ServiceCategory;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
	
	Optional<ServiceCategory> findByServiceCategoryNameIgnoreCase (String ServiceCategoryName);

}
