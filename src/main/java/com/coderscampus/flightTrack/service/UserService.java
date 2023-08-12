package com.coderscampus.flightTrack.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coderscampus.flightTrack.domain.Address;
import com.coderscampus.flightTrack.domain.User;
import com.coderscampus.flightTrack.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
    private PasswordEncoder passwordEncoder;
    
	public UserService(PasswordEncoder passwordEncoder) {
		super();
		this.passwordEncoder = passwordEncoder;
	}

	public Optional<User> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	public List<User> findByPassword(String password) {
		return userRepo.findByPassword(password);
	}

	public List<User> findByPhone(String phone) {
		return userRepo.findByphone(phone);
	}

	public List<User> firstNameAndUsername(String firstName, String username) {
		return userRepo.findByfirstNameAndUsername(firstName, username);
	}

	public List<User> firstNameAndLastname(String lastName, String username) {
		return userRepo.findByfirstNameAndUsername(lastName, username);
	}

	public User findById(Long userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		return userOpt.orElse(new User(userId, null, null, null, null, null, null, null, null));
	}

	public List<User> findAll() {
		return userRepo.findAll();
	}

	public User findBydId(Long id) {
		Optional<User> userOpt = userRepo.findById(id);
		return userOpt.orElse(new User(id, null, null, null, null, null, null, null, null));
	}

	public User saveUser(User user) {
		if (user.getAddress() == null) {
			Address address = new Address();
			address.setAddressLine1("");
			address.setAddressLine2("");
			address.setCity("");
			address.setCountry("");
			address.setState("");
			address.setZip("");
			address.setUser(user);
			address.setUserId((user.getId()));
			user.setAddress(address);
		} else {
			Address address = user.getAddress();
			address.setUser(user);
			address.setUserId(user.getId());
			user.setAddress(user.getAddress());
		}
		return userRepo.save(user);

	}

	public void delete(Long userId) {
		userRepo.deleteById(userId);

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = new User(null, username, passwordEncoder.encode("myPassword1"), username, username, username, username, null, null);
		
		return user;
	}

	

}