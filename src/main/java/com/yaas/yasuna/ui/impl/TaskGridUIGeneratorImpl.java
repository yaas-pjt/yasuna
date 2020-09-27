package com.yaas.yasuna.ui.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.HeaderRow.HeaderCell;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.yaas.yasuna.converter.DateConverter;
import com.yaas.yasuna.converter.StatusConverter;
import com.yaas.yasuna.enums.GridColumnEnums.TaskGridColumnEnum;
import com.yaas.yasuna.enums.GridHeaderEnums.TaskGridHeaderEnum;
import com.yaas.yasuna.enums.StatusEnums;
import com.yaas.yasuna.form.GridSettingForm;
import com.yaas.yasuna.form.TaskForm;
import com.yaas.yasuna.page.PersonalTaskDialogPage;
import com.yaas.yasuna.service.TaskService;
import com.yaas.yasuna.ui.GridUIGenerator;

public class TaskGridUIGeneratorImpl implements GridUIGenerator<TaskForm>{

	private static final int FLG_ON = 1;

	private static final String GRID_ID_INBOX = "inbox";
	private static final String GRID_ID_QUICK = "quick";
	private static final String GRID_ID_ASK = "ask";
	private static final String GRID_ID_NEXT = "next";

	@Override
	public void prepareBaseSetting(Grid<TaskForm> taskGrid) {

			taskGrid.setHeightByRows(true);
			taskGrid.setWidthFull();
			taskGrid.removeAllColumns();
	}

	@Override
	public void prepareHeader(GridSettingForm gridSettingForm, Grid<TaskForm> taskGrid) {
		Grid.Column<TaskForm> titleColumn;
		Grid.Column<TaskForm> memoColumn;
		Grid.Column<TaskForm> categoryColumn;
		Grid.Column<TaskForm> statusColumn;
		Grid.Column<TaskForm> sDateColumn;
		Grid.Column<TaskForm> eDateColumn;
		Grid.Column<TaskForm> deadlineColumn;

			List<Grid.Column<TaskForm>> columnList = Lists.newArrayList();

			if(FLG_ON == gridSettingForm.getTitleFlg()) {
				titleColumn =taskGrid.addColumn(TaskForm::getTitle).setHeader(TaskGridColumnEnum.TITLE.getLabel());
				titleColumn.setKey(TaskGridColumnEnum.TITLE.getLabel());
				columnList.add(titleColumn);
			}
			if(FLG_ON == gridSettingForm.getMemoFlg()) {
				memoColumn = taskGrid.addColumn(TaskForm::getMemo).setHeader(TaskGridColumnEnum.MEMO.getLabel());
				memoColumn.setKey(TaskGridColumnEnum.MEMO.getLabel());
				columnList.add(memoColumn);
			}
			if(FLG_ON == gridSettingForm.getStatusFlg()) {
				statusColumn = taskGrid.addColumn(TaskForm::getStatus).setHeader(TaskGridColumnEnum.STATUS.getLabel());
				statusColumn.setKey(TaskGridColumnEnum.STATUS.getLabel());
				columnList.add(statusColumn);
			}
			if(FLG_ON == gridSettingForm.getCategoryFlg()) {
				categoryColumn = taskGrid.addColumn(TaskForm::getCategory).setHeader(TaskGridColumnEnum.CATEGORY.getLabel());
				categoryColumn.setKey(TaskGridColumnEnum.CATEGORY.getLabel());
				columnList.add(categoryColumn);
			}
			if(FLG_ON == gridSettingForm.getsDateFlg()) {
				sDateColumn = taskGrid.addColumn(TaskForm::getsDate).setHeader(TaskGridColumnEnum.SDATE.getLabel());
				sDateColumn.setKey(TaskGridColumnEnum.SDATE.getLabel());
				columnList.add(sDateColumn);
			}
			if(FLG_ON == gridSettingForm.geteDateFlg()) {
				eDateColumn = taskGrid.addColumn(TaskForm::geteDate).setHeader(TaskGridColumnEnum.EDATE.getLabel());
				eDateColumn.setKey(TaskGridColumnEnum.EDATE.getLabel());
				columnList.add(eDateColumn);
			}
			if(FLG_ON == gridSettingForm.getDeadlineFlg()) {
				deadlineColumn = taskGrid.addColumn(TaskForm::getDeadline).setHeader(TaskGridColumnEnum.DEADLINE.getLabel());
				deadlineColumn.setKey(TaskGridColumnEnum.DEADLINE.getLabel());
				columnList.add(deadlineColumn);
			}

			Column<?>[] columns = columnList.toArray(new Column<?>[columnList.size()]);

			HeaderRow headerRow = taskGrid.prependHeaderRow();

			Div title = new Div();
			title = setTitile(taskGrid.getId());
			HeaderCell headerCell = headerRow.join(columns);
			HorizontalLayout filter = new HorizontalLayout(title);
			headerCell.setComponent(filter);
		}

	private Div setTitile(Optional<String> id) {
		Div div = new Div();

		switch(id.get()) {

		case GRID_ID_INBOX:
			div.setText(TaskGridHeaderEnum.INBOX.getLabel());
			break;

		case GRID_ID_QUICK:
			div.setText(TaskGridHeaderEnum.QUICK.getLabel());
			break;

		case GRID_ID_ASK:
			div.setText(TaskGridHeaderEnum.ASK.getLabel());
			break;

		case GRID_ID_NEXT:
			div.setText(TaskGridHeaderEnum.NEXT.getLabel());
			break;

			default:
				break;
		}

		return div;
	}

	@Override
	public void prepareEditor(Grid<TaskForm> taskGrid) {

			Binder<TaskForm> binder = new Binder<>(TaskForm.class);
			Editor<TaskForm> editor = taskGrid.getEditor();
			editor.setBinder(binder);

			TextField titleField = new TextField();
			titleField.getElement().addEventListener("keydown", event -> taskGrid.getEditor().cancel()).setFilter("event.key === 'Tab' && event.shiftKey");
			binder.bind(titleField, "title");

			TextArea memoField = new TextArea();
			memoField.getElement().addEventListener("keydown", event -> taskGrid.getEditor().cancel()).setFilter("event.key === 'Tab' && event.shiftKey");
			binder.bind(memoField, "memo");

			Select<String> statusSelect = new Select<>();
			//statusSelect.setItemLabelGenerator(StatusEnums :: getLabel);
			statusSelect.setItems(StatusEnums.YET.getLabels());



			statusSelect.getElement().addEventListener("keydown", event -> taskGrid.getEditor().cancel()).setFilter("event.key === 'Tab' && event.shiftKey");
			binder.bind(statusSelect, "status");

			DatePicker sDateField = new DatePicker();
			sDateField.getElement().addEventListener("keydown", event -> taskGrid.getEditor().cancel()).setFilter("event.key === 'Tab' && event.shiftKey");
			binder.bind(sDateField, "sDate");

			DatePicker eDateField = new DatePicker();
			eDateField.getElement().addEventListener("keydown", event -> taskGrid.getEditor().cancel()).setFilter("event.key === 'Tab' && event.shiftKey");
			binder.bind(eDateField, "eDate");

			DatePicker deadlineField = new DatePicker();
			deadlineField.getElement().addEventListener("keydown", event -> taskGrid.getEditor().cancel()).setFilter("event.key === 'Tab' && event.shiftKey");
			binder.bind(deadlineField, "deadline");

			if(taskGrid.getColumnByKey(TaskGridColumnEnum.TITLE.getLabel()) != null) {
				taskGrid.getColumnByKey(TaskGridColumnEnum.TITLE.getLabel()).setEditorComponent(titleField);
			}
			if(taskGrid.getColumnByKey(TaskGridColumnEnum.MEMO.getLabel()) != null) {
				taskGrid.getColumnByKey(TaskGridColumnEnum.MEMO.getLabel()).setEditorComponent(memoField);
			}
			if(taskGrid.getColumnByKey(TaskGridColumnEnum.STATUS.getLabel()) != null) {
				taskGrid.getColumnByKey(TaskGridColumnEnum.STATUS.getLabel()).setEditorComponent(statusSelect);
			}
			if(taskGrid.getColumnByKey(TaskGridColumnEnum.SDATE.getLabel()) != null) {
				taskGrid.getColumnByKey(TaskGridColumnEnum.SDATE.getLabel()).setEditorComponent(sDateField);
			}
			if(taskGrid.getColumnByKey(TaskGridColumnEnum.EDATE.getLabel()) != null) {
				taskGrid.getColumnByKey(TaskGridColumnEnum.EDATE.getLabel()).setEditorComponent(eDateField);
			}
			if(taskGrid.getColumnByKey(TaskGridColumnEnum.DEADLINE.getLabel()) != null) {
				taskGrid.getColumnByKey(TaskGridColumnEnum.DEADLINE.getLabel()).setEditorComponent(deadlineField);
			}
			taskGrid.addItemDoubleClickListener(event -> {
				taskGrid.getEditor().editItem(event.getItem());
			    titleField.focus();
			});
			binder.addValueChangeListener(event -> {
				taskGrid.getEditor().refresh();
			});
		}

	private void updateTask(String title, String status, String memo, LocalDate sDate, LocalDate eDate, LocalDate deadline, long seq) {

		int updatedStatus = statusConverter().convertStatusToInteger(status);
		Date updatedsDate = dateConverter().convertLocalDateToDate(sDate);
		Date updatedeDate = dateConverter().convertLocalDateToDate(eDate);
		Date updatedDeadline = dateConverter().convertLocalDateToDate(deadline);
	}

	private void changeCategory(String oldCategory, String newCategory, List<TaskForm> targetTaskList) {
		PersonalTaskDialogPage dialog = new PersonalTaskDialogPage();
		dialog.openChangeCategoryDialog(oldCategory, newCategory, targetTaskList);
	}

	private String setCategorybyGrid(Grid grid) {

		Optional<String> inboxOptional = Optional.of("inbox");
		Optional<String> quickOptional = Optional.of("quick");
		Optional<String> askOptional = Optional.of("ask");
		Optional<String> somedayOptional = Optional.of("someday");

		if(inboxOptional.equals(grid.getId())) {
			return "インボックス";
		}else if(quickOptional.equals(grid.getId())) {
			return "すぐやる";
		}else if(askOptional.equals(grid.getId())) {
			return "おねがい";
		}else if(somedayOptional.equals(grid.getId())) {
			return "次にやる";
		}else {
			return null;
		}
	}
	@Override
	public void prepareWidth(Grid<TaskForm> grid) {
		grid.getColumns().forEach(column ->column.setAutoWidth(true));
	}
	private StatusConverter statusConverter() {
		return new StatusConverter();
	}

	private DateConverter dateConverter() {
		return new DateConverter();
	}

	private TaskService taskService() {
		return new TaskService();
	}


}
