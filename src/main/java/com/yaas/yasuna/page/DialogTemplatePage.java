package com.yaas.yasuna.page;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class DialogTemplatePage extends VerticalLayout{

	abstract void buildUI();

	abstract void construct(Component... UIParts);
}
