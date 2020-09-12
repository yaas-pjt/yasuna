package com.yaas.yasuna.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class CommonView extends VerticalLayout {

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
		VerticalLayout sideMenu = new VerticalLayout();
		sideMenu.addClassName("sideMenu");
		sideMenu.setHeight("100%");
		sideMenu.setWidth("auto");
		sideMenu.setSpacing(false);
		drawer.getElement().addEventListener("click", event -> sideMenu.getStyle().set("left", "0px"));
		Icon avatar = VaadinIcon.USER.create();
		avatar.setSize("8em");
		sideMenu.add(createMenuOption("閉じる"), avatar, new Span("John Doe"),createMenuOption("個人設定"), createMenuOption("チーム"), createMenuOption("ログアウト"));
		sideMenu.setAlignItems(Alignment.CENTER);

		add(sideMenu);
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

	protected void setCommonDialogSetting(Dialog dialog) {
		dialog.setWidth("400px");
		dialog.setHeight("350px");
	}
}
