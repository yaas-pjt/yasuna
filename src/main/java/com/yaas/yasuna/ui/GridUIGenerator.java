package com.yaas.yasuna.ui;

import com.vaadin.flow.component.grid.Grid;
import com.yaas.yasuna.form.GridSettingForm;

public interface GridUIGenerator<F> {

	void prepareBaseSetting(Grid<F> grid);

	void prepareHeader(GridSettingForm gridSettingForm, Grid<F> grid);

	void prepareEditor(Grid<F> grid);
}
