package com.yaas.yasuna.dao;

import java.sql.Connection;

import com.yaas.yasuna.entity.QuickSetting;
import com.yaas.yasuna.entity.map.QuickSettingMap;

public class QuickSettingDao extends AbsDao<QuickSetting> {

	QuickSettingMap quickSettingMap = new QuickSettingMap();

	public QuickSetting getBySeq(Connection connection, long fkUserSeq) {
		QuickSetting quickSetting = new QuickSetting();

		return getBySeq(quickSetting, quickSettingMap.getMap(), connection, GET_BY_USER_SEQ, fkUserSeq);
	}

	private static final String GET_BY_USER_SEQ = "SELECT * FROM T_QUICK_SETTING WHERE FK_USER_SEQ = ?";
}
