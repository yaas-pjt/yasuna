package com.yaas.yasuna.converter.impl;

import com.yaas.yasuna.converter.ConverterLogic;
import com.yaas.yasuna.entity.QuickSetting;
import com.yaas.yasuna.form.QuickSettingForm;

public class QuickSettingConverterLogicImpl implements ConverterLogic<QuickSetting, QuickSettingForm> {

	@Override
	public QuickSettingForm convertEntityToForm(QuickSetting quickSetting) {

		QuickSettingForm quickSettingForm = new QuickSettingForm();

		quickSettingForm.setSeq(quickSetting.getSeq());
		quickSettingForm.setFkUserSeq(quickSetting.getFkUserSeq());
		quickSettingForm.setCategorySetting(quickSetting.getCategorySetting());
		quickSettingForm.setStatusSetting(quickSetting.getStatusSetting());
		quickSettingForm.setsDateSetting(quickSetting.getsDateSetting());
		quickSettingForm.setDeadlineSetting(quickSetting.getDeadlineSetting());

		return quickSettingForm;
	}

	@Override
	public QuickSetting convertFormToEntity(QuickSettingForm quickSettingForm) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
