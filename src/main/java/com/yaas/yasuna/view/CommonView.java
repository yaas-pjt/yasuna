package com.yaas.yasuna.view;

import org.vaadin.bootstrapcss.components.BsCard;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.yaas.yasuna.auth.LoginUser;
import com.yaas.yasuna.form.UserProfileForm;

public abstract class CommonView extends VerticalLayout {

	private UserProfileForm userProfile;

	//メイン画面のコンテンツ
	abstract void buildMainUI();

	//画面共通設定
	private void setCommonViewSetting() {
		setSizeFull();
		setMargin(false);
		setSpacing(false);
		setPadding(false);
	}

	//ヘッダー
	private void buildHeader(Component... components) {

		Icon menuIcon = VaadinIcon.MENU.create();
		Span systemName = new Span("やすな（仮）");

		HorizontalLayout header = new HorizontalLayout();
		header.add(menuIcon);
		header.add(systemName);

		if(components != null) {
			header.add(components);
		}

		header.expand(systemName);
		header.setPadding(true);
		header.setWidth("100%");

		add(header);
		buildSideMenu(menuIcon);
	}

	//メイン画面
	protected void buildMainContents(Component... components) {

		VerticalLayout mainContents = new VerticalLayout();
		mainContents.setClassName("maincontents");
		mainContents.add(components);
		mainContents.getStyle().set("overflow", "auto");
		add(mainContents);
}

	//フッター
	private void buildFooter(Component... components) {

		HorizontalLayout footer = new HorizontalLayout();

		if(components != null) {
			footer.add(components);
		}

		footer.setJustifyContentMode(JustifyContentMode.CENTER);
		footer.setWidth("100%");
		add(footer);
	}

	//サイドメニュー
	private void buildSideMenu(Icon drawer) {
		setUserProfile();

		VerticalLayout sideMenu = new VerticalLayout();
		sideMenu.addClassName("sideMenu");
		sideMenu.setHeight("100%");
		sideMenu.setWidth("auto");
		sideMenu.setSpacing(false);
		drawer.getElement().addEventListener("click", event -> sideMenu.getStyle().set("left", "0px"));
		Icon avatar = VaadinIcon.USER.create();
		avatar.setSize("8em");
		BsCard profileCard = new BsCard();
		profileCard.setCardHeaderText("プロフィール");
		Div nameDiv = new Div(new Text("氏名：" + userProfile.getFirstName() + "　" + userProfile.getLastName()));
		Div orgDiv = new Div(new Text("組織：" + userProfile.getOrg()));
		profileCard.add(nameDiv, orgDiv);

		sideMenu.add(createMenuOption("閉じる"), avatar, profileCard, createPersonalSettingButton(), createLogoutButton());
		sideMenu.setAlignItems(Alignment.CENTER);

		add(sideMenu);
	}

	private Button createPersonalSettingButton() {
		Button personalSettingButton = new Button("個人設定");
		personalSettingButton.setWidth("100%");
		personalSettingButton.addClickListener(ev -> personalSettingButton.getElement().getParent().getStyle().set("left", "-1000px"));
		personalSettingButton.addClickListener(ev -> getUI().get().navigate("personalsetting"));

		return personalSettingButton;
	}

	private Button createLogoutButton() {
		Button logoutButton = new Button("ログアウト");
		logoutButton.setWidth("100%");
		logoutButton.addClickListener(event -> logout());
		logoutButton.getElement().setAttribute("title", "Logout (Ctrl+L)");

		return logoutButton;
	}

	private void logout() {
		//AccessControlFactory.getInstance().createAccessControl().signOut(sideMenu);

		//VaadinSession.getCurrent().getSession().removeAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_USER);
		//VaadinSession.getCurrent().getSession().removeAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_GRID_SETTING);
		//VaadinSession.getCurrent().getSession().removeAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_QUICK_SETTING);
		//VaadinSession.getCurrent().getSession().removeAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_USER_PROFILE);
		getUI().get().navigate("login");
		getUI().get().getSession().close();
	}

	private Button createMenuOption(String title) {
		Button sideMenuButton = new Button(title);
		sideMenuButton.setWidth("100%");
		sideMenuButton.addClickListener(ev -> sideMenuButton.getElement().getParent().getStyle().set("left", "-1000px"));
		sideMenuButton.addClickListener(ev -> Notification.show("Button " + title + " clicked."));
		return sideMenuButton;
	}

	//パーツを組み立てて画面完成
	protected void construct(Component[] headerParts, Component[] mainParts, Component[] footerParts) {
		setCommonViewSetting();
		buildHeader(headerParts);
		buildMainContents(mainParts);
		buildFooter(footerParts);
	}

	//ダイアログの共通設定
	protected void setCommonDialogSetting(Dialog dialog) {
		dialog.setWidth("80%");
		dialog.setHeight("100%");
		//dialog.getElement().getStyle().set("overflow", "auto");
		dialog.setModal(false);
	}

	private void setUserProfile() {
		userProfile = LoginUser.getUserProfile();
		}
}
