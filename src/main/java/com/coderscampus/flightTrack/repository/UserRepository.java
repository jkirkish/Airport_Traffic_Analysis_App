package com.coderscampus.flightTrack.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coderscampus.flightTrack.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	// select * from users where username = username
	//User findByUsername(String username);
	
	//select * from users where password = : password
	List<User>findByPassword(String password);
	
	//select * from users where firstName = : firstName
	List<User> findByfirstName(String firstName);
	
	//select * from users where lastName = : lastName
	List<User>findBylastName(String lastName);
	
	//select * from email where email = : email
	List<User>findByemail(String email);
	
	//select * from phone where phone = : phone
	List<User>findByphone(String phone);
	
	//select * from users where firstname = : name and username = :username
	List<User>findByfirstNameAndUsername(String firstName, String username);
	
	//select * from users where lastname = :lastName and username 
	List<User>findBylastNameAndUsername(String lastName, String username);

	List<User> findByUsername(String username);

	
	
	
	
}
