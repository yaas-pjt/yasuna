package com.yaas.yasuna.entity.map;

import java.util.Map;

import com.google.common.collect.Maps;

public class TaskMap extends AbsObjectMap {

	private Map<String, String> map;

	private static final String KEY_SEQ = "t_seq";
	private static final String KEY_UID = "t_uid";
	private static final String KEY_PARENT_ID = "t_parent_id";
	private static final String KEY_TITLE = "t_title";
	private static final String KEY_MEMO = "t_memo";
	private static final String KEY_STATUS = "t_status";
	private static final String KEY_CATEGORY = "t_category";
	private static final String KEY_START_DATE = "t_start_date";
	private static final String KEY_END_DATE = "t_end_date";
	private static final String KEY_DEADLINE_DATE = "t_deadline";
	private static final String KEY_PRC_DATE = "prc_date";

	private static final String VALUE_SEQ = "seq";
	private static final String VALUE_UID = "uid";
	private static final String VALUE_PARENT_ID = "parentId";
	private static final String VALUE_TITLE = "title";
	private static final String VALUE_MEMO = "memo";
	private static final String VALUE_STATUS = "status";
	private static final String VALUE_CATEGORY = "category";
	private static final String VALUE_START_DATE = "startDate";
	private static final String VALUE_END_DATE = "endDate";
	private static final String VALUE_DEADLINE_DATE = "deadline";
	private static final String VALUE_PRC_DATE = "prcDate";

	public TaskMap() {
		super();

		map = Maps.newTreeMap();

		map.put(KEY_SEQ, VALUE_SEQ);
		map.put(KEY_UID, VALUE_UID);
		map.put(KEY_PARENT_ID, VALUE_PARENT_ID);
		map.put(KEY_TITLE, VALUE_TITLE);
		map.put(KEY_STATUS, VALUE_STATUS);
		map.put(KEY_CATEGORY, VALUE_CATEGORY);
		map.put(KEY_MEMO, VALUE_MEMO);
		map.put(KEY_START_DATE, VALUE_START_DATE);
		map.put(KEY_END_DATE, VALUE_END_DATE);
		map.put(KEY_DEADLINE_DATE, VALUE_DEADLINE_DATE);
		map.put(KEY_PRC_DATE, VALUE_PRC_DATE);
	}

	@Override
	public Map<String, String> getMap() {

		return map;
	}
}
