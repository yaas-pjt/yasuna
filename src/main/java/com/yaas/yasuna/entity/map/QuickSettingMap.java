package com.yaas.yasuna.entity.map;

import java.util.Map;

import com.google.common.collect.Maps;

public class QuickSettingMap extends AbsObjectMap {
	private Map<String, String> map;

	private static final String KEY_SEQ = "seq";
	private static final String KEY_USER_SEQ = "fk_user_seq";
	private static final String KEY_CATEGORY_SETTING = "category_setting";
	private static final String KEY_STATUS_SETTING = "status_setting";
	private static final String KEY_SDATE_SETTING = "sdate_setting";
	private static final String KEY_DEADLINE_SETTING = "deadline_setting";
	private static final String KEY_PRC_DATE = "prc_date";

	private static final String VALUE_SEQ = "seq";
	private static final String VALUE_USER_SEQ = "fkUserSeq";
	private static final String VALUE_CATEGORY_SETTING = "categorySetting";
	private static final String VALUE_STATUS_SETTING = "statusSetting";
	private static final String VALUE_SDATE_SETTING = "sDateSetting";
	private static final String VALUE_DEADLINE_SETTING = "deadlineSetting";
	private static final String VALUE_PRC_DATE = "prcDate";

	@Override
	public Map<String, String> getMap() {

		map = Maps.newTreeMap();
		map.put(KEY_SEQ, VALUE_SEQ);
		map.put(KEY_USER_SEQ, VALUE_USER_SEQ);
		map.put(KEY_CATEGORY_SETTING, VALUE_CATEGORY_SETTING);
		map.put(KEY_STATUS_SETTING, VALUE_STATUS_SETTING);
		map.put(KEY_SDATE_SETTING, VALUE_SDATE_SETTING);
		map.put(KEY_DEADLINE_SETTING, VALUE_DEADLINE_SETTING);
		map.put(KEY_PRC_DATE, VALUE_PRC_DATE);

		return map;
	}

}
