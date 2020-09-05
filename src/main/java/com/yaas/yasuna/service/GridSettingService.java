package com.yaas.yasuna.service;

import com.yaas.yasuna.converter.ConverterLogic;
import com.yaas.yasuna.converter.impl.GridSettingConverterLogicImpl;
import com.yaas.yasuna.dao.GridSettingDao;
import com.yaas.yasuna.entity.GridSetting;
import com.yaas.yasuna.form.GridSettingForm;
import com.yaas.yasuna.transaction.Transaction;

public class GridSettingService {

	public GridSettingForm getByUserSeq(long fkUserSeq) {
		GridSetting gridSetting = new GridSetting();
		gridSetting = gridSettingDao().getByUserSeq(transaction().getConnection(), fkUserSeq);

		return converter().convertEntityToForm(gridSetting);

	}

	private GridSettingDao gridSettingDao() {
		return new GridSettingDao();
	}

	private ConverterLogic<GridSetting, GridSettingForm> converter() {
		return new GridSettingConverterLogicImpl();
	}

	private Transaction transaction() {
		return new Transaction();
	}
}
