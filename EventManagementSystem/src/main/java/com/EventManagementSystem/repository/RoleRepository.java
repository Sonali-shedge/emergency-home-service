package com.EventManagementSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EventManagementSystem.entity.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long> {

	Optional<Roles> findByRoleName(String roleName);

}
