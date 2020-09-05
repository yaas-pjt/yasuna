package com.yaas.yasuna.converter.impl;

import com.yaas.yasuna.converter.ConverterLogic;
import com.yaas.yasuna.entity.User;
import com.yaas.yasuna.form.UserForm;

public class UserConverterLogicImpl implements ConverterLogic<User, UserForm> {

	@Override
	public UserForm convertEntityToForm(User user) {

		UserForm userForm = new UserForm();

		userForm.setSeq(user.getSeq());
		userForm.setUserId(user.getUserId());
		userForm.setDisplayName(user.getDisplayName());
		userForm.setStatus(user.getStatus());
		userForm.setPassword(user.getPassword());

		return userForm;
	}

	@Override
	public User convertFormToEntity(UserForm userForm) {

		return null;
	}

}
