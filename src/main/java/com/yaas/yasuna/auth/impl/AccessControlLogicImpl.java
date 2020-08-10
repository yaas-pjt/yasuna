package com.yaas.yasuna.auth.impl;

import com.yaas.yasuna.auth.AccessControlLogic;
import com.yaas.yasuna.encoder.PasswordEncoder;
import com.yaas.yasuna.entity.User;
import com.yaas.yasuna.service.UserService;

public class AccessControlLogicImpl implements AccessControlLogic {

	@Override
	public boolean signIn(String username, String password) {

		if(username == null || username.equals("")) {
			return false;
		}

		User user = userService().getById(username);

		if(user == null) {
			return false;
		}

		if(!user.getPassword().equals(passwordEncoder().encodePassword(password))) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isUserSignedIn() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean isUserInRole(String role) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public String getPrincipalName() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void signOut() {
		// TODO 自動生成されたメソッド・スタブ

	}

	private UserService userService() {
		return new UserService();
	}

	private PasswordEncoder passwordEncoder() {
		return new PasswordEncoder();
	}

}
