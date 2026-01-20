package com.EventManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EventManagementSystem.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	
	List<Address> findByUserEmail(String email);

}
