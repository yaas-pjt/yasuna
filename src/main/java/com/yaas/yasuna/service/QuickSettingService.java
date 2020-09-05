package com.yaas.yasuna.service;

import com.yaas.yasuna.converter.ConverterLogic;
import com.yaas.yasuna.converter.impl.QuickSettingConverterLogicImpl;
import com.yaas.yasuna.dao.QuickSettingDao;
import com.yaas.yasuna.entity.QuickSetting;
import com.yaas.yasuna.form.QuickSettingForm;
import com.yaas.yasuna.transaction.Transaction;

public class QuickSettingService {

	public QuickSettingForm getByUserSeq(long fkUserSeq) {
		QuickSetting quickSetting = new QuickSetting();
		quickSetting = quickSettingDao().getBySeq(transaction().getConnection(), fkUserSeq);

		return converter().convertEntityToForm(quickSetting);
	}


	private QuickSettingDao quickSettingDao() {
		return new QuickSettingDao();
	}

	private ConverterLogic<QuickSetting, QuickSettingForm> converter() {
		return new QuickSettingConverterLogicImpl();
	}

	private Transaction transaction() {
		return new Transaction();
	}
}
