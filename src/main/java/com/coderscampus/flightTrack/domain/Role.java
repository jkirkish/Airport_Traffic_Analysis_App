package com.coderscampus.flightTrack.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority{
	
	private Long roleId;
	private String authority;
	private List<User> users = new ArrayList<>();
	
	public Role() {
		super();
	}
	public Role(String authority) {
		this.authority = authority;
	}
	public Role(Long roleId, String authority) {
		this.roleId = roleId;
		this.authority = authority;
	}
	public Role(Long roleId, String authority, List<User> users) {
		super();
		this.roleId = roleId;
		this.authority = authority;
		this.users = users;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="role_id")
	public Long getRoleId() {
		return this.roleId;
	}
    public void setRoleId(Long roleId) {
    	this.roleId = roleId;
    }
	@Override
	public String getAuthority() {
		return this.authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	@ManyToMany(mappedBy = "authorities")
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}