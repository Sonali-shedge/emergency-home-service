package com.EventManagementSystem.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	private String userName;

	private String email;

	private String password;

	private String phone;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private List<Address> address = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER ,  cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id")
	private Roles role;

	@PrePersist
	public void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	public void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE" +role.getRoleName()));
	}

	@Override
	public String getUsername() {return null;};
		

	
	public String getUserName() {
		
		return this.userName=userName;
	}
}
