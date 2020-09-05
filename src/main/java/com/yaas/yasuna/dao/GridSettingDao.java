package com.yaas.yasuna.dao;

import java.sql.Connection;

import com.yaas.yasuna.entity.GridSetting;
import com.yaas.yasuna.entity.map.GridSettingMap;

public class GridSettingDao extends AbsDao<GridSetting>{

	GridSettingMap gridSettingMap = new GridSettingMap();

	public GridSetting getByUserSeq(Connection connection, long fkUserSeq) {

		GridSetting gridSetting = new GridSetting();

		return getBySeq(gridSetting, gridSettingMap.getMap(), connection, GET_BY_USER_SEQ, fkUserSeq);
	}



	private static final String GET_BY_USER_SEQ = "SELECT * FROM T_GRID_SETTING WHERE FK_USER_SEQ = ?";
}
