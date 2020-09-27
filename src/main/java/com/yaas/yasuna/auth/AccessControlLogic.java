package com.yaas.yasuna.auth;

import java.io.Serializable;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public interface AccessControlLogic extends Serializable {

	boolean signIn(String username, String password);

	boolean isUserSignedIn();

	boolean isUserInRole(String role);

	String getPrincipalName();

	void signOut(VerticalLayout sideMenu);
}
