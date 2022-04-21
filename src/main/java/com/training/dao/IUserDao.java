package com.training.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.entity.UserInfoEntity;

public interface IUserDao extends JpaRepository<UserInfoEntity, Long> {

	UserInfoEntity  findByUsernameAndPassword(String username, String password);
	UserInfoEntity  findByUsername(String username);
}
