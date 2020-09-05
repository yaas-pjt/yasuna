package com.yaas.yasuna.entity.map;

import java.util.Map;

import com.google.common.collect.Maps;

public class UserMap extends AbsObjectMap {

	private static final String KEY_SEQ = "seq";
	private static final String KEY_U_ID = "user_id";
	private static final String KEY_DISPLAY_NAME = "display_name";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_STATUS = "status";
	private static final String KEY_ROLE_ID = "role_id";
	private static final String KEY_PRC_DATE = "prc_date";

	private static final String VALUE_SEQ = "seq";
	private static final String VALUE_U_ID = "userId";
	private static final String VALUE_DISPLAY_NAME = "displayName";
	private static final String VALUE_PASSWORD = "password";
	private static final String VALUE_STATUS = "status";
	private static final String VALUE_ROLE_ID = "roleId";
	private static final String VALUE_PRC_DATE = "prcDate";
	private Map<String, String> map;

	public UserMap() {
		super();

		map = Maps.newTreeMap();

		map.put(KEY_SEQ, VALUE_SEQ);
		map.put(KEY_U_ID, VALUE_U_ID);
		map.put(KEY_DISPLAY_NAME, VALUE_DISPLAY_NAME);
		map.put(KEY_PASSWORD, VALUE_PASSWORD);
		map.put(KEY_STATUS, VALUE_STATUS);
		map.put(KEY_ROLE_ID, VALUE_ROLE_ID);
		map.put(KEY_PRC_DATE, VALUE_PRC_DATE);

	}

	@Override
	public Map<String, String> getMap() {

		return map;
	}

}
