package com.yaas.yasuna.page;

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

import test.AccessControl;
import test.AccessControlFactory;

/**
 * The main view contains a button and a click listener.
 */
@Route("login2")
@PageTitle("Login2")
@CssImport("./styles/shared-styles.css")
public class SecondView extends VerticalLayout {


    private AccessControl accessControl;

    public SecondView() {
        accessControl = AccessControlFactory.getInstance().createAccessControl();
        buildUI();
    }

    private void buildUI() {
        setSizeFull();
        setClassName("login-screen");

        // login form, centered in the available part of the screen
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(this::login);
        loginForm.addForgotPasswordListener(
                event -> Notification.show("Hint: same as username"));

        // layout to center login form when there is sufficient screen space
        FlexLayout centeringLayout = new FlexLayout();
        centeringLayout.setSizeFull();
        centeringLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        centeringLayout.setAlignItems(Alignment.CENTER);
        centeringLayout.add(loginForm);

        // information text about logging in
        Component loginInformation = buildLoginInformation();

        add(loginInformation);
        add(centeringLayout);


    }

    private Component buildLoginInformation() {
        VerticalLayout loginInformation = new VerticalLayout();
        loginInformation.setClassName("login-information");

        H1 loginInfoHeader = new H1("Login Information");
        loginInfoHeader.setWidth("100%");
        Span loginInfoText = new Span(
                "Log in as \"admin\" to have full access. Log in with any " +
                        "other username to have read-only access. For all " +
                        "users, the password is same as the username.");
        loginInfoText.setWidth("100%");
        loginInformation.add(loginInfoHeader);
        loginInformation.add(loginInfoText);

        return loginInformation;
    }

    private void login(LoginForm.LoginEvent event) {
        if (accessControl.signIn(event.getUsername(), event.getPassword())) {
            getUI().get().navigate("mainview");
        } else {
            event.getSource().setError(true);
        }
    }
}
