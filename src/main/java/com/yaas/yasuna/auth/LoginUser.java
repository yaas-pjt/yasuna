package com.yaas.yasuna.auth;

import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.yaas.yasuna.consts.SessionAttributeConsts;
import com.yaas.yasuna.exception.YasunaException;
import com.yaas.yasuna.form.GridSettingForm;
import com.yaas.yasuna.form.QuickSettingForm;
import com.yaas.yasuna.form.UserForm;
import com.yaas.yasuna.form.UserProfileForm;

public final class LoginUser {


	public static UserForm getUserData() {
		UserForm userForm = (UserForm) getCurrentRequest().getWrappedSession().getAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_USER);
		if (userForm == null) {

			throw new YasunaException("セッション情報の取得に失敗しました。");
		} else {

			return userForm;
			}
		}

	public static GridSettingForm getUserGridSetting() {
		GridSettingForm gridSettingForm = (GridSettingForm) getCurrentRequest().getWrappedSession().getAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_GRID_SETTING);
		if (gridSettingForm == null) {
			throw new YasunaException("セッション情報の取得に失敗しました。");

		} else {

			return gridSettingForm;
			}
		}

	public static QuickSettingForm getUserQuickSetting() {
		QuickSettingForm quickSettingForm = (QuickSettingForm) getCurrentRequest().getWrappedSession().getAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_QUICK_SETTING);
		if (quickSettingForm == null) {
			throw new YasunaException("セッション情報の取得に失敗しました。");

		} else {

			return quickSettingForm;
			}
		}

	public static UserProfileForm getUserProfile() {
		UserProfileForm UserProfileForm = (UserProfileForm) getCurrentRequest().getWrappedSession().getAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_USER_PROFILE);
		if (UserProfileForm == null) {
			throw new YasunaException("セッション情報の取得に失敗しました。");

		} else {

			return UserProfileForm;
			}
		}

	public static void set(UserForm userForm, GridSettingForm gridSettingForm, QuickSettingForm quickSettingForm, UserProfileForm UserProfileForm) {
		if (userForm == null) {
			getCurrentRequest().getWrappedSession().removeAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_USER);
			} else {
				getCurrentRequest().getWrappedSession().setAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_USER, userForm);
			}

		if (gridSettingForm == null) {
			getCurrentRequest().getWrappedSession().removeAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_GRID_SETTING);
			} else {
				getCurrentRequest().getWrappedSession().setAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_GRID_SETTING, gridSettingForm);
			}

		if (quickSettingForm == null) {
			getCurrentRequest().getWrappedSession().removeAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_QUICK_SETTING);
			} else {
				getCurrentRequest().getWrappedSession().setAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_QUICK_SETTING, quickSettingForm);
			}

		if (UserProfileForm == null) {
			getCurrentRequest().getWrappedSession().removeAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_USER_PROFILE);
			} else {
				getCurrentRequest().getWrappedSession().setAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_USER_PROFILE, UserProfileForm);
			}
		}

	private static VaadinRequest getCurrentRequest() {
		VaadinRequest request = VaadinService.getCurrentRequest();
		if (request == null) {
			throw new YasunaException("セッション情報が見つかりません。");
			}
		return request;
		}
}
