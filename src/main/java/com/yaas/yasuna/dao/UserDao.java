package com.yaas.yasuna.dao;

import java.sql.Connection;
import java.util.List;

import com.yaas.yasuna.entity.User;
import com.yaas.yasuna.entity.map.UserMap;

public class UserDao extends AbsDao<User> {

	private UserMap userMap = new UserMap();

	public User getById(Connection connection, String userId) {
		User user = new User();

		return getById(user, userMap.getMap(), connection, GET_BY_ID, userId);
	}

	public int updatePassword(Connection connection, List<Object> paramList) {


		return update(connection, UPDATE_PASSWORD, paramList);
	}


	private static final String GET_BY_ID = "select * from ebdb.u_user where user_id = ?";
	private static final String UPDATE_PASSWORD = "update u_user set password = ? where seq = ?";
}
