package com.training.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserInfoEntity {
	@Id
	@Column(name = "username", nullable = false, unique = true)
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	private String userRole;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	
}