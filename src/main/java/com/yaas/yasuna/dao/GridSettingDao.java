package com.yaas.yasuna.dao;

import java.sql.Connection;
import java.util.List;

import com.yaas.yasuna.entity.GridSetting;
import com.yaas.yasuna.entity.map.GridSettingMap;

public class GridSettingDao extends AbsDao<GridSetting>{

	GridSettingMap gridSettingMap = new GridSettingMap();

	public GridSetting getByUserSeq(Connection connection, long fkUserSeq) {

		GridSetting gridSetting = new GridSetting();

		return getBySeq(gridSetting, gridSettingMap.getMap(), connection, GET_BY_USER_SEQ, fkUserSeq);
	}

	public int updateGridSetting(Connection connection, List<Object> paramList) {

		return update(connection, UPDATE_SETTING, paramList);
	}


	private static final String GET_BY_USER_SEQ = "SELECT * FROM t_grid_setting WHERE FK_USER_SEQ = ?";
	private static final String UPDATE_SETTING = "update t_grid_setting set title_flg = ?, status_flg = ?, category_flg = ?, memo_flg = ?, sdate_flg = ?, edate_flg = ?, deadline_flg = ? where fk_user_seq = ?";

}
