package com.yaas.yasuna.auth;

import com.yaas.yasuna.entity.User;

public final class LoginUser {

	private static User user;

	private LoginUser() {
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		user = user;
	}

}
