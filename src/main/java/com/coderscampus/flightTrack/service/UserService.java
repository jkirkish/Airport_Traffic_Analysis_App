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

	
	private UserRepository userRepo;
   
    
	public UserService(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	public User findByUsername(String username) {
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

	public User findById(Integer userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		return userOpt.orElse(new User(userId, null, null, null, null, null, null, null, null));
	}

	public List<User> findAll() {
		return userRepo.findAll();
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
			address.setId((user.getId()));
			user.setAddress(address);
		} else {
			Address address = user.getAddress();
			address.setUser(user);
			address.setId(user.getId());
			user.setAddress(user.getAddress());
		}
		return userRepo.save(user);

	}

	public void delete(Integer userId) {
		userRepo.deleteById(userId);

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepo.findByUsername(username);
		
		if(user == null)throw new UsernameNotFoundException("Bad Credentials"); 
		return user;
	}

	

}