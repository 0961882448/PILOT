package com.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.training.common.util.CommonUtil;
import com.training.dao.IUserDao;
import com.training.entity.UserInfoEntity;
import com.training.service.UserInfoService;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	IUserDao userDao;

	@Override
	public UserInfoEntity login(String userName, String password) {
		// TODO Auto-generated method stub
		System.out.println(CommonUtil.ecryptMD5(password));
		UserInfoEntity user = userDao.findByUsernameAndPassword(userName,password);//CommonUtil.ecryptMD5(password));
		return user;
	}

	@Override
	public UserInfoEntity login(String username) {
		// TODO Auto-generated method stub
		UserInfoEntity user = userDao.findByUsername(username);
		return user;
	}
}