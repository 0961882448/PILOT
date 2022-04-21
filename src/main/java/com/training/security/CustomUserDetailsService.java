package com.training.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.training.common.constant.Constants;
import com.training.common.util.CommonUtil;
import com.training.dao.IUserDao;
import com.training.entity.UserInfoEntity;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	IUserDao userInfoService;

	@Transactional
	public UserDetails loadUserByUsername(String username) {

		UserDetails userDetails = null;
		try {
			boolean isLoginRequest = CommonUtil.getSessionAttribute(Constants.KEY_IS_LOGIN_REQUEST) != null;

			// Check current request is login or remember me checking 
			// to prevent login successfully in the case username is correct but password is incorrect
			if (!isLoginRequest) {
				UserInfoEntity userInfo = userInfoService.findByUsername(username);

				// Update last login time
				if (userInfo != null) {
					CommonUtil.setSessionAttribute("userInfo", userInfo);
					userDetails = new User(userInfo.getUsername(), StringUtils.EMPTY, true, true, true, true, getGrantedAuthorities(userInfo));
				}
			}
		} catch (Exception e) {
			throw new UsernameNotFoundException("Username not found");
		}
		return userDetails;
	}

	/**
	 * Get  Granted Authorities of user
	 * 
	 * @param user
	 * @return List<GrantedAuthority>
	 */
	private List<GrantedAuthority> getGrantedAuthorities(UserInfoEntity user) {

		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		grantedAuths.add(new SimpleGrantedAuthority(user.getUserRole()));
		return grantedAuths;
	}
}
