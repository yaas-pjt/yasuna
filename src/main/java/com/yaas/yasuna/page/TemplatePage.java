package com.yaas.yasuna.page;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

public abstract class TemplatePage extends VerticalLayout{

	abstract void buildMainUI();

	public void construct(Component[] headerParts, Component[] mainParts, Component[] footerParts) {
		setDefaultSetting();
		buildHeader(headerParts);
		buildMainContents(mainParts);
		buildFooter(footerParts);
	}

	private void setDefaultSetting() {
		setSizeFull();
		setMargin(false);
		setSpacing(false);
		setPadding(false);
	}

	// ヘッダー
	private void buildHeader(Component... components) {

		Icon drawer = VaadinIcon.MENU.create();
		Span title = new Span("やすな（テスト中）");
		HorizontalLayout header = new HorizontalLayout();
		header.add(drawer);
		header.add(title);

		if(components != null) {
			header.add(components);
		}

		header.expand(title);
		header.setPadding(true);
		header.setWidth("100%");

		add(header);
		buildSideMenu(drawer);
	}

	protected void buildMainContents(Component... components) {

			VerticalLayout mainContents = new VerticalLayout();
			mainContents.setClassName("maincontents");
			mainContents.add(components);
			add(mainContents);
	}

	//フッター
	private void buildFooter(Component... components) {

		Tab actionButton1 = new Tab(VaadinIcon.USER.create(), new Span("個人タスク"));
		Tab actionButton2 = new Tab(VaadinIcon.USERS.create(), new Span("チームタスク"));
		Tab actionButton3 = new Tab(VaadinIcon.CALENDAR.create(), new Span("スケジュール"));
		Tabs buttonBar = new Tabs(actionButton1, actionButton2, actionButton3);
		HorizontalLayout footer = new HorizontalLayout(components);
		footer.setJustifyContentMode(JustifyContentMode.CENTER);
		footer.setWidth("100%");

		add(footer);
	}

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
}
