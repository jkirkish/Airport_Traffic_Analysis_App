package com.coderscampus.flightTrack.domain;



import java.time.LocalDate;
import java.util.Objects;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails{
	
	
	private Long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private LocalDate registrationDate;
	private Address address;
	private Boolean accountNonExpired;
	private Boolean accountNonLocked;
	//private Collection<? extends GrantedAuthority> authorities;
	private Boolean enabled;
	private Boolean expired;
	private Collection<? extends GrantedAuthority> authorities;
    
	public User(Long id, String username, String password, String firstName, String lastName, String email,
			String phone, LocalDate registrationDate, Address address) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.registrationDate = registrationDate;
		this.address = address;
		
	}
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	@Column(unique=true)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
    @Override
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public LocalDate getRegistrationDate() {
		return registrationDate;
	}
	
	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	@OneToOne(mappedBy = "user",cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval = true)
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", phone=" + phone + ", registrationDate="
				+ registrationDate + ", address=" + address + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return null;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> priviledges) {
		this.authorities= priviledges;
	}
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}
	public void setAccountNonExpired(Boolean expired) {
		
		this.accountNonExpired = expired;
	}
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}
    public void setAccountNonLocked(Boolean locked) {
		
		this.accountNonLocked = locked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}
	public void setCredentialsNonExpired(Boolean expire) {
		this.expired = expire;
	}
	@Override
	public boolean isEnabled() {
		
		return true;
	}
	public void setEnabled(Boolean enable) {
		this.enabled = enable;
	}     

}