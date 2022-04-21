package com.training.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.training.common.constant.Constants;
import com.training.common.util.CommonUtil;
import com.training.dao.IUserDao;
import com.training.entity.UserInfoEntity;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private static Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	@Autowired
	IUserDao userInfoService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info("[START] : authenticate");
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		UsernamePasswordAuthenticationToken usernamePassAuthToken = null;

		UserInfoEntity userInfo = userInfoService.findByUsernameAndPassword(username, password);
		if (userInfo!=null) {
			//CommonUtil.setSessionAttribute("userInfo", userInfo);
			List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
			grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			usernamePassAuthToken = new UsernamePasswordAuthenticationToken(username, StringUtils.EMPTY, grantedAuths);
		}
		CommonUtil.setSessionAttribute(Constants.KEY_IS_LOGIN_REQUEST, true);
		return usernamePassAuthToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}