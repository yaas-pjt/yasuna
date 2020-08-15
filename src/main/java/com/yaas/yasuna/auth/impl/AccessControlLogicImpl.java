package com.yaas.yasuna.auth.impl;

import com.vaadin.flow.server.VaadinService;
import com.yaas.yasuna.auth.AccessControlLogic;
import com.yaas.yasuna.consts.SessionAttributeConsts;
import com.yaas.yasuna.encoder.PasswordEncoder;
import com.yaas.yasuna.form.UserForm;
import com.yaas.yasuna.service.UserService;

public class AccessControlLogicImpl implements AccessControlLogic {

	@Override
	public boolean signIn(String username, String password) {

		if(username == null || username.equals("")) {
			return false;
		}

		UserForm loginUser = userService().getById(username);

		if(loginUser == null) {
			return false;
		}

		if(!loginUser.getPassword().equals(passwordEncoder().encodePassword(password))) {
			return false;
		}

		VaadinService.getCurrentRequest().getWrappedSession().setAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_USER, loginUser);

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
