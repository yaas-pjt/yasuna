package com.yaas.yasuna.dao;

import java.sql.Connection;

import com.yaas.yasuna.entity.User;
import com.yaas.yasuna.entity.map.UserMap;

public class UserDao extends AbsDao<User> {

	private UserMap userMap = new UserMap();

	public User getById(Connection connection, String userId) {
		User user = new User();

		return getById(user, userMap.getMap(), connection, GET_BY_ID, userId);
	}

	private static final String GET_BY_ID = "SELECT * FROM U_USER WHERE USER_ID = ?";
}
