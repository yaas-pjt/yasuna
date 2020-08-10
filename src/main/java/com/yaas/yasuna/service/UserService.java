package com.yaas.yasuna.service;

import com.yaas.yasuna.dao.UserDao;
import com.yaas.yasuna.entity.User;
import com.yaas.yasuna.transaction.Transaction;

public class UserService {

	public User getById(String userId) {

		return userDao().getById(transaction().getConnection(), userId);
	}

	private UserDao userDao() {
		return new UserDao();
	}

	private Transaction transaction() {
		return new Transaction();
	}
}
