package com.yaas.yasuna.service;

import java.util.List;

import com.google.common.collect.Lists;
import com.yaas.yasuna.converter.ConverterLogic;
import com.yaas.yasuna.converter.impl.QuickSettingConverterLogicImpl;
import com.yaas.yasuna.dao.QuickSettingDao;
import com.yaas.yasuna.entity.QuickSetting;
import com.yaas.yasuna.form.QuickSettingForm;
import com.yaas.yasuna.transaction.Transaction;

public class QuickSettingService {

	private static final int RESULT_FAILURE = 0;

	public QuickSettingForm getByUserSeq(long fkUserSeq) {
		QuickSetting quickSetting = new QuickSetting();
		quickSetting = quickSettingDao().getBySeq(transaction().getConnection(), fkUserSeq);

		return converter().convertEntityToForm(quickSetting);
	}

	public boolean updateSetting(int category, int status, int sDate, int deadline, long fkUserSeq) {
		List<Object> paramList = Lists.newArrayList();
		paramList.add(category);
		paramList.add(status);
		paramList.add(sDate);
		paramList.add(deadline);
		paramList.add(fkUserSeq);

		if(RESULT_FAILURE == quickSettingDao().updateSetting(transaction().getConnection(), paramList)) {
			return false;
		} else {
			return true;
		}
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
