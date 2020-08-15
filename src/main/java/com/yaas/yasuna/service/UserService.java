package com.yaas.yasuna.service;

import com.yaas.yasuna.converter.ConverterLogic;
import com.yaas.yasuna.converter.impl.UserConverterLogicImpl;
import com.yaas.yasuna.dao.UserDao;
import com.yaas.yasuna.entity.User;
import com.yaas.yasuna.form.UserForm;
import com.yaas.yasuna.transaction.Transaction;

public class UserService {

	public UserForm getById(String userId) {

		User user = userDao().getById(transaction().getConnection(), userId);

		return userConverter().convertEntityToForm(user);
	}

	private UserDao userDao() {
		return new UserDao();
	}

	private ConverterLogic<User, UserForm> userConverter() {
		return new UserConverterLogicImpl();
	}

	private Transaction transaction() {
		return new Transaction();
	}
}
