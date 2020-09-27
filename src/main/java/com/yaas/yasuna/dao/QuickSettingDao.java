package com.yaas.yasuna.dao;

import java.sql.Connection;
import java.util.List;

import com.yaas.yasuna.entity.QuickSetting;
import com.yaas.yasuna.entity.map.QuickSettingMap;

public class QuickSettingDao extends AbsDao<QuickSetting> {

	QuickSettingMap quickSettingMap = new QuickSettingMap();

	public QuickSetting getBySeq(Connection connection, long fkUserSeq) {
		QuickSetting quickSetting = new QuickSetting();

		return getBySeq(quickSetting, quickSettingMap.getMap(), connection, GET_BY_USER_SEQ, fkUserSeq);
	}

	public int updateSetting(Connection connection, List<Object> paramList) {

		return update(connection, UPDATE_SETTING, paramList);
	}

	private static final String GET_BY_USER_SEQ = "SELECT * FROM t_quick_setting WHERE FK_USER_SEQ = ?";
	private static final String UPDATE_SETTING = "update t_quick_setting set category_setting = ?, status_setting = ?, sdate_setting = ?, deadline_setting = ? where fk_user_seq = ?";
}
