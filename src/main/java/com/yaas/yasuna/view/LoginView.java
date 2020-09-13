package com.yaas.yasuna.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.yaas.yasuna.auth.AccessControlLogic;
import com.yaas.yasuna.auth.impl.AccessControlLogicImpl;

@PWA(name = "yasuna", shortName = "yasuna",
iconPath = "icons/yasuna03.png", 	description = "うざいタスクを楽しく管理", backgroundColor = "#ff8c00")
@Route("login3")
@PageTitle("login")
@CssImport("./styles/shared-styles.css")
public class LoginView extends VerticalLayout{

	private AccessControlLogic accessControl;

	public LoginView() {
		buildUI();
	}

	public void buildUI() {
		setSizeFull();
		setClassName("login-screen");

		// ログインフォームを作ってる
		LoginForm loginForm = new LoginForm();
		loginForm.addLoginListener(this::login);
		loginForm.addForgotPasswordListener(event -> Notification.show("Hint: same as username"));

		// サイズ指定してレイアウトを決めている。センターに設定して、対象のフォームを最後にaddする。
		FlexLayout centeringLayout = new FlexLayout();
		centeringLayout.setSizeFull();
		centeringLayout.setJustifyContentMode(JustifyContentMode.CENTER);
		centeringLayout.setAlignItems(Alignment.CENTER);
		centeringLayout.add(loginForm);

		// ヘッダーにでかでかと出てるアレ
		Component loginInformation = buildLoginInformation();
		add(loginInformation);
		add(centeringLayout);
	}

	private Component buildLoginInformation() {
		VerticalLayout loginInformation = new VerticalLayout();
		loginInformation.setClassName("login-information");
		H1 loginInfoHeader = new H1("工事中だお（´・ω・｀）");
		loginInfoHeader.setWidth("100%");
		Span loginInfoText = new Span( "まだ何もできてないけど、そのうち何かできるんじゃないかな？");
		loginInfoText.setWidth("100%");
		loginInformation.add(loginInfoHeader);
		loginInformation.add(loginInfoText);

		return loginInformation;
		}

	private void login(LoginForm.LoginEvent event) {

		accessControl = new AccessControlLogicImpl();

		if (accessControl.signIn(event.getUsername(), event.getPassword())) {
			getUI().get().navigate("personaltask");
			} else {
				event.getSource().setError(true);
				}
		}

}
