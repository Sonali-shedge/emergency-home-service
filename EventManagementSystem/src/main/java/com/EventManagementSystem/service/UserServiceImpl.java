package com.EventManagementSystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.EventManagementSystem.dto.AddressRequestDTO;
import com.EventManagementSystem.dto.UserRequestDTO;
import com.EventManagementSystem.dto.UserResponseDTO;
import com.EventManagementSystem.entity.Address;
import com.EventManagementSystem.entity.Roles;
import com.EventManagementSystem.entity.ServiceCategory;
import com.EventManagementSystem.entity.ServiceProvider;
import com.EventManagementSystem.entity.User;
import com.EventManagementSystem.entity.Zone;
import com.EventManagementSystem.enumT.ApprovalStatus;
import com.EventManagementSystem.enumT.AvailabilityStatus;
import com.EventManagementSystem.enumT.Role;
import com.EventManagementSystem.exception.RoleNotFoundException;
import com.EventManagementSystem.exception.UserNotFoundException;
import com.EventManagementSystem.repository.RoleRepository;
import com.EventManagementSystem.repository.ServiceCategoryRepository;
import com.EventManagementSystem.repository.ServiceProviderRepository;
import com.EventManagementSystem.repository.UserRepository;
import com.EventManagementSystem.repository.ZoneRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	ZoneRepository zoneRepository;
	
	@Autowired
	ServiceCategoryRepository serviceCategoryRepository;
	
	@Autowired
	ServiceProviderRepository  serviceProviderRepository;

//	@Override
//	public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
//
//		 if (userRequestDTO.getRole()== Role.SERVICE_PROVIDER ) {
//		        throw new RuntimeException(
//		            "Cannot register SERVICE_PROVIDER via this API. Only ADMIN can create providers."
//		        );
//		    }
//		 Roles roleEntity = roleRepository.findByRoleName(userRequestDTO.getRole().name())
//		            .orElseThrow(() -> new RoleNotFoundException("Role does not exist"));
//		User user = new User();
//		user.setUserName(userRequestDTO.getUserName());
//		user.setEmail(userRequestDTO.getEmail());
//		user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
//		user.setPhone(userRequestDTO.getPhone());
//		user.setRole(roleEntity);
//
//
//		
//		List<Address> addressList = new ArrayList<>();
//
//	    for (AddressRequestDTO addrDTO : userRequestDTO.getAddress()) {
//
//	        Address address = new Address();
//	        address.setHouseNumber(addrDTO.getHouseNumber());
//	        address.setStreet(addrDTO.getStreet());
//	        address.setArea(addrDTO.getArea());
//	        address.setCity(addrDTO.getCity());
//	        address.setState(addrDTO.getState());
//	        address.setPincode(addrDTO.getPincode());
//
//	        // 🔥 PHOTON API CALL
//	        String fullAddress = buildFullAddress(addrDTO);
//	        System.out.println("Geocoding address: " + fullAddress);
//	        double[] latLng = photonGeocodingService.getLatLong(fullAddress);
//
//	        address.setLatitude(latLng[0]);
//	        address.setLongitude(latLng[1]);
//
//	        address.setUser(user);
//	        addressList.add(address);
//	    }
//
//	    user.setAddress(addressList);
//
//	    // Step 2e: Save user
//	    userRepository.save(user);
//
//	    // Step 2f: Map to response DTO (implement mapToResponse)
//	    return modelMapper.map(user, UserResponseDTO.class);
//	
//	}
//	
//	@Override
//	public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
//
//	    // 1️⃣ Block SERVICE_PROVIDER registration
//	    if (userRequestDTO.getRole() == Role.SERVICE_PROVIDER) {
//	        throw new RuntimeException(
//	            "Cannot register SERVICE_PROVIDER via this API. Only ADMIN can create providers."
//	        );
//	    }
//
//	    // 2️⃣ Fetch role
//	    Roles roleEntity = roleRepository
//	            .findByRoleName(userRequestDTO.getRole().name())
//	            .orElseThrow(() -> new RoleNotFoundException("Role does not exist"));
//
//	    // 3️⃣ Create User
//	    User user = new User();
//	    user.setUserName(userRequestDTO.getUserName());
//	    user.setEmail(userRequestDTO.getEmail());
//	    user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
//	    user.setPhone(userRequestDTO.getPhone());
//	    user.setRole(roleEntity);
//
//	    // 4️⃣ Address list
//	    List<Address> addressList = new ArrayList<>();
//
//	    for (AddressRequestDTO addrDTO : userRequestDTO.getAddress()) {
//
//	        Address address = new Address();
//	        address.setHouseNumber(addrDTO.getHouseNumber());
//	        address.setStreet(addrDTO.getStreet());
//	        address.setArea(addrDTO.getArea());
//	        address.setCity(addrDTO.getCity());
//	        address.setState(addrDTO.getState());
//	        address.setPincode(addrDTO.getPincode());
//
//	        // 5️⃣ Build address for LocationIQ
//	        String fullAddress = buildFullAddress(addrDTO);
//	        System.out.println("Geocoding address: " + fullAddress);
//
//	        // 6️⃣ LocationIQ API call (SAFE)
//	        double[] latLng = locationIQGeocodingService.getLatLong(fullAddress);
//
//	        address.setLatitude(latLng[0]);
//	        address.setLongitude(latLng[1]);
//
//	        address.setUser(user);
//	        addressList.add(address);
//	    }
//
//	    user.setAddress(addressList);
//
//	    // 7️⃣ Save user
//	    userRepository.save(user);
//
//	    // 8️⃣ Return response
//	    return modelMapper.map(user, UserResponseDTO.class);
//	}

	


@Override
@Transactional
public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {

    // 🔐 1. ROLE-SPECIFIC VALIDATION
    if (userRequestDTO.getRole() == Role.SERVICE_PROVIDER) {

        if (userRequestDTO.getExperineceInYears() == null ||
            userRequestDTO.getServiceCategoryId() == null)
//            userRequestDTO.getZoneIds() == null ||
//            userRequestDTO.getZoneIds().isEmpty())
            {

            throw new RuntimeException(
                "Experience, Service Category and Zones are required for Service Provider"
            );
        }
    }

    // 👤 2. CREATE USER
    User user = new User();
    user.setUserName(userRequestDTO.getUserName());
    user.setEmail(userRequestDTO.getEmail());
    user.setPhone(userRequestDTO.getPhone());
    user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

    Roles roleEntity = roleRepository
            .findByRoleName(userRequestDTO.getRole().name())
            .orElseThrow(() ->
                    new RoleNotFoundException("Role does not exist"));

    user.setRole(roleEntity);

    // 🏠 3. ADDRESS + ZONE MAPPING
    List<Address> addressList = new ArrayList<>();

    for (AddressRequestDTO addrDTO : userRequestDTO.getAddress()) {

        Address address = new Address();
        address.setHouseNumber(addrDTO.getHouseNumber());
        address.setStreet(addrDTO.getStreet());
        address.setState(addrDTO.getState());
        address.setPincode(addrDTO.getPincode());
        address.setUser(user);

        String city = addrDTO.getCity();
        String zoneName = addrDTO.getZoneName();

        if (city == null || zoneName == null) {
            throw new RuntimeException("City and Zone are required");
        }

        Zone zone = zoneRepository
                .findByCityIgnoreCaseAndZoneNameIgnoreCase(
                        city.trim(),
                        zoneName.trim()
                )
                .orElseThrow(() ->
                        new RuntimeException("Zone not found"));

        address.setZone(zone);
        addressList.add(address);
    }

    user.setAddress(addressList);

    // 💾 4. SAVE USER (MANDATORY BEFORE SERVICE PROVIDER)
    userRepository.save(user);

    // 🛠️ 5. SERVICE PROVIDER CREATION (ROLE BASED)
    if (userRequestDTO.getRole() == Role.SERVICE_PROVIDER) {

        ServiceProvider serviceProvider = new ServiceProvider();

        serviceProvider.setUser(user);
        serviceProvider.setProviderName(user.getUserName());
        serviceProvider.setEmail(user.getEmail());
        serviceProvider.setExperineceInYears(
                userRequestDTO.getExperineceInYears()
        );

        // 🔗 SERVICE CATEGORY
        ServiceCategory category = serviceCategoryRepository
                .findById(userRequestDTO.getServiceCategoryId())
                .orElseThrow(() ->
                        new RuntimeException("Service category not found"));

        serviceProvider.setServiceCategory(category);

        // 🔗 ZONES
        List<Zone> zones = zoneRepository
                .findAllById(userRequestDTO.getZoneIds());

        if (zones.isEmpty()) {
            throw new RuntimeException("No valid zones found");
        }

        serviceProvider.setServiceZones(zones);

        // 🔒 DEFAULT SECURITY STATES
        serviceProvider.setApprovalStatus(ApprovalStatus.PENDING);
        serviceProvider.setAvailabilityStatus(AvailabilityStatus.UNAVAILABLE);
        serviceProvider.setIsVerified(false);
        serviceProvider.setRating(0.0);

        serviceProviderRepository.save(serviceProvider);
    }

    // 🎯 6. RESPONSE
    return modelMapper.map(user, UserResponseDTO.class);
}




	public UserResponseDTO filterUser(Long uId) {
		User user = userRepository.findById(uId).orElseThrow(() -> new UserNotFoundException("user not found"));
		return modelMapper.map(user, UserResponseDTO.class);
	}

	public List<UserResponseDTO> allUser() {
		List<User> user = userRepository.findAll();
		return user.stream().map(dto -> modelMapper.map(dto, UserResponseDTO.class)).collect(Collectors.toList());
	}

	public UserResponseDTO updateUser(Long uId, UserRequestDTO userRequestDTO) {
		User userInDB = userRepository.findById(uId)
				.orElseThrow(() -> new UserNotFoundException("user not found with this id " + uId));
		userInDB.setUserName(userRequestDTO.getUserName());
		userInDB.setEmail(userRequestDTO.getEmail());
		userInDB.setPassword(userRequestDTO.getPassword());
		userInDB.setPhone(userRequestDTO.getPhone());

		User updatedUser = userRepository.save(userInDB);
		return modelMapper.map(updatedUser, UserResponseDTO.class);
	}

	



	

}
