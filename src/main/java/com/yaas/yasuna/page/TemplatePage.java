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

	abstract void buildUI();

	abstract void construct(Component... UIParts);

	// ヘッダー
	public void buildHeader() {

		Icon drawer = VaadinIcon.MENU.create();
		Span title = new Span("やすな（テスト中）");
		Icon help = VaadinIcon.QUESTION_CIRCLE.create();
		HorizontalLayout header = new HorizontalLayout(drawer, title, help);
		header.expand(title);
		header.setPadding(true);
		header.setWidth("100%");

		add(header);

		buildSideMenu(drawer);
	}

	//フッター
	public void buildFooter() {

		Tab actionButton1 = new Tab(VaadinIcon.USER.create(), new Span("個人タスク"));
		Tab actionButton2 = new Tab(VaadinIcon.USERS.create(), new Span("チームタスク"));
		Tab actionButton3 = new Tab(VaadinIcon.CALENDAR.create(), new Span("スケジュール"));
		Tabs buttonBar = new Tabs(actionButton1, actionButton2, actionButton3);
		HorizontalLayout footer = new HorizontalLayout(buttonBar);
		footer.setJustifyContentMode(JustifyContentMode.CENTER);
		footer.setWidth("100%");

		add(footer);
	}

	public void buildSideMenu(Icon drawer) {
		VerticalLayout sideMenu = new VerticalLayout();
		sideMenu.addClassName("sideMenu");
		sideMenu.setHeight("100%");
		sideMenu.setWidth("auto");
		sideMenu.setSpacing(false);
		drawer.getElement().addEventListener("click", ev->sideMenu.getStyle().set("left", "0px"));
		Icon avatar = VaadinIcon.USER.create();
		avatar.setSize("4em");
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
