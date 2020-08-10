package com.yaas.yasuna.entity.map;

import java.util.Map;

public class TaskMap extends AbsObjectMap {

	private Map<String, String> map;

	private static final String KEY_SEQ = "SEQ";
	private static final String KEY_USER_SEQ = "USER_SEQ";
	private static final String KEY_TASK_ID = "TASK_ID";
	private static final String KEY_PARENT_TASK_ID = "PARENT_TASK_ID";
	private static final String KEY_TITLE = "PRC_TITLE";
	private static final String KEY_STATUS = "STATUS";
	private static final String KEY_MEMO = "MEMO";
	private static final String KEY_START_DATE = "START_DATE";
	private static final String KEY_END_DATE = "END_DATE";
	private static final String KEY_DEADLINE_DATE = "DEADLINE_DATE";
	private static final String KEY_PRC_DATE = "PRC_DATE";

	private static final String VALUE_SEQ = "seq";
	private static final String VALUE_USER_SEQ = "userSeq";
	private static final String VALUE_TASK_ID = "taskId";
	private static final String VALUE_PARENT_TASK_ID = "ParentTaskId";
	private static final String VALUE_TITLE = "title";
	private static final String VALUE_STATUS = "status";
	private static final String VALUE_MEMO = "memo";
	private static final String VALUE_START_DATE = "startDate";
	private static final String VALUE_END_DATE = "endDate";
	private static final String VALUE_DEADLINE_DATE = "deadlineDate";
	private static final String VALUE_PRC_DATE = "prcDate";

	public TaskMap() {
		super();

		map.put(KEY_SEQ, VALUE_SEQ);
		map.put(KEY_USER_SEQ, VALUE_USER_SEQ);
		map.put(KEY_TASK_ID, VALUE_TASK_ID);
		map.put(KEY_PARENT_TASK_ID, VALUE_PARENT_TASK_ID);
		map.put(KEY_TITLE, VALUE_TITLE);
		map.put(KEY_STATUS, VALUE_STATUS);
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
