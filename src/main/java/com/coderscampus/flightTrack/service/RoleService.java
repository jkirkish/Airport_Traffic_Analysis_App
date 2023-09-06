package com.coderscampus.flightTrack.service;

import org.springframework.stereotype.Service;

import com.coderscampus.flightTrack.domain.Role;
import com.coderscampus.flightTrack.domain.User;
import com.coderscampus.flightTrack.repository.RoleRepository;

@Service
public class RoleService {
	
	private RoleRepository roleRepo;

	public RoleService(RoleRepository roleRepo) {
		super();
		this.roleRepo = roleRepo;
	}
	
	public void addNormalUser(User user) {
		Role role = new Role();
		role.setAuthority("USER");
		System.out.println("Role is: " + role.getAuthority() );
		role.getUsers().add(user);
		user.getAuthorities().add(role);
		System.out.println("size is:" + user.getAuthorities().size());
		roleRepo.save(role);
	}
	
	
	public void addAdminUser(User user) {
        // Check if there are any existing users with "ADMIN" role
        boolean isAdminExists = roleRepo.existsByAuthority("ADMIN");

        if (!isAdminExists) {
            Role role = new Role();
            role.setAuthority("ADMIN");
            role.getUsers().add(user);
            user.getAuthorities().add(role);
            roleRepo.save(role);
        } else {
           System.out.println("Admin user already exists!");
        }
    }
}
