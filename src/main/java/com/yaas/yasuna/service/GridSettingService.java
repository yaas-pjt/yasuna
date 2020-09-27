package com.yaas.yasuna.service;

import java.util.List;

import com.google.common.collect.Lists;
import com.yaas.yasuna.converter.ConverterLogic;
import com.yaas.yasuna.converter.impl.GridSettingConverterLogicImpl;
import com.yaas.yasuna.dao.GridSettingDao;
import com.yaas.yasuna.entity.GridSetting;
import com.yaas.yasuna.form.GridSettingForm;
import com.yaas.yasuna.transaction.Transaction;

public class GridSettingService {

	private static final int RESULT_FAILURE = 0;

	public GridSettingForm getByUserSeq(long fkUserSeq) {
		GridSetting gridSetting = new GridSetting();
		gridSetting = gridSettingDao().getByUserSeq(transaction().getConnection(), fkUserSeq);

		return converter().convertEntityToForm(gridSetting);

	}

	public boolean updateGridSetting(int titleFlg, int statusFlg, int categoryFlg, int memoFlg, int sDateFlg, int eDateFlg, int deadlineFlg, long fkUserSeq) {
		List<Object> paramList = Lists.newArrayList();
		paramList.add(titleFlg);
		paramList.add(statusFlg);
		paramList.add(categoryFlg);
		paramList.add(memoFlg);
		paramList.add(sDateFlg);
		paramList.add(eDateFlg);
		paramList.add(deadlineFlg);
		paramList.add(fkUserSeq);

		if(RESULT_FAILURE == gridSettingDao().updateGridSetting(transaction().getConnection(), paramList)) {
			return false;
		} else {
			return true;
		}
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
