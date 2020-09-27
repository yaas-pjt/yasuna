package com.yaas.yasuna.service;

import java.util.List;

import com.google.common.collect.Lists;
import com.yaas.yasuna.converter.ConverterLogic;
import com.yaas.yasuna.converter.impl.UserConverterLogicImpl;
import com.yaas.yasuna.dao.UserDao;
import com.yaas.yasuna.encoder.PasswordEncoder;
import com.yaas.yasuna.entity.User;
import com.yaas.yasuna.form.UserForm;
import com.yaas.yasuna.transaction.Transaction;

public class UserService {

	private static final int RESULT_FAILURE = 0;

	public UserForm getById(String userId) {

		User user = userDao().getById(transaction().getConnection(), userId);

		if(user == null) {
			return null;
		}

		return userConverter().convertEntityToForm(user);
	}

	public boolean updatePassword(String password, long seq) {
		List<Object> paramList = Lists.newArrayList();
		paramList.add(passwordEncoder().encodePassword(password));
		paramList.add(seq);

		if(RESULT_FAILURE == userDao().updatePassword(transaction().getConnection(), paramList)) {
			return false;
		} else {
			return true;
		}

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

	private PasswordEncoder passwordEncoder() {
		return new PasswordEncoder();
	}
}
