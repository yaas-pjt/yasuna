package com.yaas.yasuna.converter.impl;

import com.yaas.yasuna.converter.ConverterLogic;
import com.yaas.yasuna.entity.GridSetting;
import com.yaas.yasuna.form.GridSettingForm;

public class GridSettingConverterLogicImpl  implements ConverterLogic<GridSetting, GridSettingForm>{

	@Override
	public GridSettingForm convertEntityToForm(GridSetting gridSetting) {

		GridSettingForm gridSettingForm = new GridSettingForm();

		gridSettingForm.setSeq(gridSetting.getSeq());
		gridSettingForm.setFkUserSeq(gridSetting.getFkUserSeq());
		gridSettingForm.setTitleFlg(gridSetting.getTitleFlg());
		gridSettingForm.setMemoFlg(gridSetting.getMemoFlg());
		gridSettingForm.setCategoryFlg(gridSetting.getCategoryFlg());
		gridSettingForm.setStatusFlg(gridSetting.getStatusFlg());
		gridSettingForm.setsDateFlg(gridSetting.getsDateFlg());
		gridSettingForm.seteDateFlg(gridSetting.geteDateFlg());
		gridSettingForm.setDeadlineFlg(gridSetting.getDeadlineFlg());


		return gridSettingForm;
	}

	@Override
	public GridSetting convertFormToEntity(GridSettingForm gridSettingForm) {
		return null;
	}

}
