package com.yaas.yasuna.entity.map;

import java.util.Map;

import com.google.common.collect.Maps;

public class UserMap extends AbsObjectMap {

	private static final String KEY_SEQ = "u_seq";
	private static final String KEY_U_ID = "u_id";
	private static final String KEY_DISPLAY_NAME = "u_display_name";
	private static final String KEY_PASSWORD = "u_password";
	private static final String KEY_STATUS = "u_status";
	private static final String KEY_ROLE_ID = "u_role_id";
	private static final String KEY_PRC_DATE = "u_prc_date";

	private static final String VALUE_SEQ = "seq";
	private static final String VALUE_U_ID = "uid";
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
