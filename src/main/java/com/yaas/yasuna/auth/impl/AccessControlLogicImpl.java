package com.yaas.yasuna.auth.impl;

import com.vaadin.flow.server.VaadinService;
import com.yaas.yasuna.auth.AccessControlLogic;
import com.yaas.yasuna.consts.SessionAttributeConsts;
import com.yaas.yasuna.encoder.PasswordEncoder;
import com.yaas.yasuna.form.GridSettingForm;
import com.yaas.yasuna.form.QuickSettingForm;
import com.yaas.yasuna.form.UserForm;
import com.yaas.yasuna.service.GridSettingService;
import com.yaas.yasuna.service.QuickSettingService;
import com.yaas.yasuna.service.UserService;

public class AccessControlLogicImpl implements AccessControlLogic {

	@Override
	public boolean signIn(String username, String password) {

		if(username == null || username.equals("")) {
			return false;
		}

		UserForm loginUser = userService().getById(username);
		GridSettingForm gridSetting = gridSettingService().getByUserSeq(loginUser.getSeq());
		QuickSettingForm quickSetting = quickSettingService().getByUserSeq(loginUser.getSeq());

		if(!loginUser.getPassword().equals(passwordEncoder().encodePassword(password))) {
			return false;
		}

		setAttribute(loginUser, gridSetting, quickSetting);

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

	private void setAttribute(UserForm loginUser, GridSettingForm gridSetting, QuickSettingForm quickSetting) {
		VaadinService.getCurrentRequest().getWrappedSession().setAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_USER, loginUser);
		VaadinService.getCurrentRequest().getWrappedSession().setAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_GRID_SETTING, gridSetting);
		VaadinService.getCurrentRequest().getWrappedSession().setAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_QUICK_SETTING, quickSetting);
	}

	private UserService userService() {
		return new UserService();
	}

	private GridSettingService gridSettingService() {
		return new GridSettingService();
	}

	private QuickSettingService quickSettingService() {
		return new QuickSettingService();
	}

	private PasswordEncoder passwordEncoder() {
		return new PasswordEncoder();
	}

}
