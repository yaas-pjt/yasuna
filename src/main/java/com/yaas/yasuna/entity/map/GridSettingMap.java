package com.yaas.yasuna.entity.map;

import java.util.Map;

import com.google.common.collect.Maps;

public class GridSettingMap extends AbsObjectMap {

	private Map<String, String> map;

	private static final String KEY_SEQ = "grid_seq";
	private static final String KEY_USER_SEQ = "fk_user_seq";
	private static final String KEY_TITLE_FLG = "title_flg";
	private static final String KEY_STATUS_FLG = "status_flg";
	private static final String KEY_MEMO_FLG = "memo_flg";
	private static final String KEY_CATEGORY_FLG = "category_flg";
	private static final String KEY_SDATE_FLG = "sdate_flg";
	private static final String KEY_EDATE_FLG = "edate_flg";
	private static final String KEY_DEADLINE_FLG = "deadline_flg";
	private static final String KEY_PRC_DATE = "prc_date";

	private static final String VALUE_SEQ = "seq";
	private static final String VALUE_USER_SEQ = "fkUserSeq";
	private static final String VALUE_TITLE_FLG = "titleFlg";
	private static final String VALUE_STATUS_FLG = "statusFlg";
	private static final String VALUE_MEMO_FLG = "memoFlg";
	private static final String VALUE_CATEGORY_FLG = "categoryFlg";
	private static final String VALUE_SDATE_FLG = "sdateFlg";
	private static final String VALUE_EDATE_FLG = "edateFlg";
	private static final String VALUE_DEADLINE_FLG = "deadlineFlg";
	private static final String VALUE_PRC_DATE = "prcDate";


	@Override
	public Map<String, String> getMap() {

		map = Maps.newTreeMap();
		map.put(KEY_SEQ, VALUE_SEQ);
		map.put(KEY_USER_SEQ, VALUE_USER_SEQ);
		map.put(KEY_TITLE_FLG, VALUE_TITLE_FLG);
		map.put(KEY_STATUS_FLG, VALUE_STATUS_FLG);
		map.put(KEY_MEMO_FLG, VALUE_MEMO_FLG);
		map.put(KEY_CATEGORY_FLG, VALUE_CATEGORY_FLG);
		map.put(KEY_SDATE_FLG, VALUE_SDATE_FLG);
		map.put(KEY_EDATE_FLG, VALUE_EDATE_FLG);
		map.put(KEY_DEADLINE_FLG, VALUE_DEADLINE_FLG);
		map.put(KEY_PRC_DATE, VALUE_PRC_DATE);

		return map;
	}

}
