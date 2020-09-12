package com.yaas.yasuna.page;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.yaas.yasuna.enums.CategoryEnums;
import com.yaas.yasuna.enums.StatusEnums;
import com.yaas.yasuna.form.QuickSettingForm;
import com.yaas.yasuna.form.TaskForm;
import com.yaas.yasuna.service.TaskService;

public class PersonalTaskDialogPage extends DialogTemplatePage{

	@Override
	public void buildUI() {

	}

	private static final int PARENT_TASK = 0;

	private TaskForm selectedTask;
	private int selectedCategory;
	private int selectedStatus;

	private boolean result = false;


	public void openAddTaskFormat(long uid, List<TaskForm> taskList) {

		Dialog dialog = new Dialog();
		dialog.setWidth("400px");
		dialog.setHeight("350px");

		VerticalLayout format = new VerticalLayout();
		TextField titleField = new TextField();
		titleField.setLabel("タイトル");

		Select<TaskForm> pTaskSelect = new Select<>();
		pTaskSelect.setLabel("親タスク");
		pTaskSelect.setItemLabelGenerator(TaskForm :: getTitle);
		pTaskSelect.setItems(taskList);
		pTaskSelect.addValueChangeListener(event -> getTask(event.getValue()));

		Select<CategoryEnums> categorySelect = new Select<>();
		categorySelect.setLabel("分類");
		categorySelect.setItemLabelGenerator(CategoryEnums :: getLabel);
		categorySelect.setItems(CategoryEnums.values());
		categorySelect.addValueChangeListener(event -> getCategory(event.getValue()));

		Select<StatusEnums> statusSelect = new Select<>();
		statusSelect.setLabel("ステータス");
		statusSelect.setItemLabelGenerator(StatusEnums :: getLabel);
		statusSelect.setItems(StatusEnums.values());
		statusSelect.addValueChangeListener(event -> getStatus(event.getValue()));

		TextArea memoField = new TextArea("メモ");
		DatePicker sDateField = new DatePicker();
		LocalDate now = LocalDate.now();
		sDateField.setValue(now);
		sDateField.setLabel("開始日");
		DatePicker deadlineField = new DatePicker();
		deadlineField.setLabel("対応期限");


		Button registerButton = new Button("追加",  event -> register(uid, selectedTask.getParentTaskId(), selectedCategory, titleField.getValue(), selectedStatus, memoField.getValue(), sDateField.getValue(), deadlineField.getValue()));
		format.add(titleField, pTaskSelect, categorySelect, statusSelect, memoField,sDateField,  deadlineField, registerButton);
		dialog.add(format);
		dialog.open();
	}

	public void openQuickAddTaskDialog(long uid, QuickSettingForm quickSettingForm) {
		Dialog dialog = new Dialog();
		dialog.setWidth("400px");
		dialog.setHeight("650px");

		VerticalLayout format = new VerticalLayout();
		TextField titleField = new TextField();
		titleField.setLabel("タイトル");

		TextArea memoField = new TextArea("メモ");

		//デフォルト設定から登録値を生成
		long defaultParentTaskId = 0;
		int defaultCategory = quickSettingForm.getCategorySetting();
		int defaultStatus = quickSettingForm.getStatusSetting();
		LocalDate defaultsDate  = LocalDate.now().plusDays(quickSettingForm.getsDateSetting());
		LocalDate defaultDeadline  = LocalDate.now().plusDays(quickSettingForm.getDeadlineSetting());

		//デフォルト値をプリセット
		Select<CategoryEnums> categorySelect = new Select<>();
		categorySelect.setLabel("分類");
		categorySelect.setItemLabelGenerator(CategoryEnums :: getLabel);
		categorySelect.setItems(CategoryEnums.values());
		categorySelect.addValueChangeListener(event -> getCategory(event.getValue()));

		for(CategoryEnums c : CategoryEnums.values()) {
			if(c.getId() == defaultCategory) {
				categorySelect.setValue(c);
				break;
			}
		}

		Select<StatusEnums> statusSelect = new Select<>();
		statusSelect.setLabel("ステータス");
		statusSelect.setItemLabelGenerator(StatusEnums :: getLabel);
		statusSelect.setItems(StatusEnums.values());
		statusSelect.addValueChangeListener(event -> getStatus(event.getValue()));

		for(StatusEnums s : StatusEnums.values()) {
			if(s.getId() == defaultStatus) {
				statusSelect.setValue(s);
				break;
			}
		}

		DatePicker sDateField = new DatePicker();
		sDateField.setValue(defaultsDate);
		sDateField.setLabel("開始日");
		DatePicker deadlineField = new DatePicker();
		deadlineField.setValue(defaultDeadline);
		deadlineField.setLabel("対応期限");

		Button registerButton = new Button("追加",  event -> register(uid, defaultParentTaskId, defaultCategory, titleField.getValue(), defaultStatus, memoField.getValue(), defaultsDate, defaultDeadline));
		format.add(titleField, categorySelect, statusSelect, memoField, sDateField,  deadlineField, registerButton);
		dialog.add(format);
		dialog.open();
	}

	public void openChangeCategoryDialog(String oldCategory, String newCategory, List<TaskForm> targetTaskList) {
		Dialog dialog = new Dialog();
		dialog.setWidth("400px");
		dialog.setHeight("650px");

		dialog.add(new Text("タスクの分類が【" + oldCategory + "】から【" + newCategory +  "】に更新されます。対応期限を再設定してください。"));

		VerticalLayout format = new VerticalLayout();

		DatePicker deadlineField = new DatePicker();
		LocalDate now = LocalDate.now();
		deadlineField.setValue(now.plusDays(1));
		deadlineField.setLabel("対応期限");

		Button changeButton = new Button("更新",  event -> changeCategory(dialog, newCategory, deadlineField.getValue(), targetTaskList));

		format.add(deadlineField, changeButton);
		dialog.add(format);
		dialog.open();

	}

	private void changeCategory(Dialog dialog, String newCategory, LocalDate deadline, List<TaskForm> targetTaskList) {

		int category = convertCategory(newCategory);
		Date deadlineDate = Date.from(deadline.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<Long> seqList = Lists.newArrayList();

		for(TaskForm taskForm : targetTaskList) {
			seqList.add(taskForm.getSeq());
		}

		dialog.close();
	}

	private int convertCategory(String category) {

		switch(category) {

		case "インボックス":

			return 1;

		case "すぐやる":

			return 2;

		case "おねがい":

			return 3;

		case "次にやる":

			return 4;

		default :

			return -1000;
		}
	}

	public void openChangeStatusDialog(List<TaskForm> targetTaskList) {
		Dialog dialog = new Dialog();
		dialog.setWidth("400px");
		dialog.setHeight("650px");

		Grid<TaskForm> targetGrid = new Grid<>(TaskForm.class);
		targetGrid.removeAllColumns();
		targetGrid.addColumn(TaskForm::getTitle).setHeader("タイトル");
		targetGrid.addColumn(TaskForm::getStatus).setHeader("ステータス");
		targetGrid.setItems(targetTaskList);

		Select<StatusEnums> statusSelect = new Select<>();
		statusSelect.setLabel("ステータス");
		statusSelect.setItemLabelGenerator(StatusEnums :: getLabel);
		statusSelect.setItems(StatusEnums.values());
		statusSelect.addValueChangeListener(event -> getStatus(event.getValue()));

		Button updateButton = new Button("更新",  event -> updateStatus(dialog, selectedStatus, targetTaskList));

		dialog.add(new Text("選択したタスクのステータスを一括更新します。"));
		dialog.add(targetGrid);
		dialog.add(statusSelect);
		dialog.add(updateButton);
		dialog.open();
	}

	public boolean openUpdateDeadlineDialog(List<TaskForm> targetTaskList) {
		Dialog dialog = new Dialog();
		dialog.setWidth("400px");
		dialog.setHeight("650px");

		Grid<TaskForm> targetGrid = new Grid<>(TaskForm.class);
		targetGrid.removeAllColumns();
		targetGrid.addColumn(TaskForm::getTitle).setHeader("タイトル");
		targetGrid.addColumn(TaskForm::getDeadline).setHeader("対応期限");
		targetGrid.setItems(targetTaskList);

		DatePicker deadlineField = new DatePicker();
		LocalDate now = LocalDate.now();
		deadlineField.setValue(now);
		deadlineField.setLabel("対応期限");

		Button updateButton = new Button("更新",  event -> updateDeadline(dialog, deadlineField.getValue(), targetTaskList));

		dialog.add(new Text("選択したタスクの対応期限を一括更新します。"));
		dialog.add(targetGrid);
		dialog.add(deadlineField);
		dialog.add(updateButton);
		dialog.open();

		return result;
	}


	public void openDeleteTasksDialog(long uid, List<TaskForm> targetTaskList) {
		Dialog dialog = new Dialog();
		dialog.setWidth("400px");
		dialog.setHeight("650px");

		Grid<TaskForm> targetGrid = new Grid<>(TaskForm.class);
		targetGrid.removeAllColumns();
		targetGrid.addColumn(TaskForm::getTitle).setHeader("タイトル");
		targetGrid.setItems(targetTaskList);

		Button deleteButton = new Button("削除",  event -> deleteTasks(dialog, targetTaskList));

		dialog.add(new Text("選択したタスクを削除します。よろしいですか？"));
		dialog.add(targetGrid);
		dialog.add(deleteButton);
		dialog.open();

	}


	public void editTask(long uid, TaskForm selectedTask, List<TaskForm> taskList) {
		Dialog dialog = new Dialog();
		dialog.setWidth("400px");
		dialog.setHeight("650px");

		VerticalLayout format = new VerticalLayout();
		TextField titleField = new TextField();
		titleField.setValue(selectedTask.getTitle());
		titleField.setLabel("タイトル");

		Select<TaskForm> pTaskSelect = new Select<>();
		pTaskSelect.setLabel("親タスク");
		pTaskSelect.setItemLabelGenerator(TaskForm :: getTitle);
		pTaskSelect.setItems(taskList);
		pTaskSelect.addValueChangeListener(event -> getTask(event.getValue()));

		TaskForm parentTask = new TaskForm();

		if(PARENT_TASK != selectedTask.getParentTaskId()) {

			for(TaskForm task : taskList) {
				if(selectedTask.getParentTaskId() == task.getSeq()) {
					parentTask = task;
					continue;
				}
			}
		}

		pTaskSelect.setValue(parentTask);

		Select<CategoryEnums> categorySelect = new Select<>();
		categorySelect.setLabel("分類");
		categorySelect.setItemLabelGenerator(CategoryEnums :: getLabel);
		categorySelect.setItems(CategoryEnums.values());
		categorySelect.addValueChangeListener(event -> getCategory(event.getValue()));

		for(CategoryEnums c : CategoryEnums.values()) {
			//if(c.getId() == selectedTask.getCategory()) {
				//categorySelect.setValue(c);
			//}
		}

		Select<StatusEnums> statusSelect = new Select<>();
		statusSelect.setLabel("ステータス");
		statusSelect.setItemLabelGenerator(StatusEnums :: getLabel);
		statusSelect.setItems(StatusEnums.values());
		statusSelect.addValueChangeListener(event -> getStatus(event.getValue()));

		for(StatusEnums s : StatusEnums.values()) {
			if(s.getLabel().equals(selectedTask.getStatus())) {
				statusSelect.setValue(s);
			}
		}
		TextArea memoField = new TextArea("メモ");
		if(selectedTask.getMemo() != null) {
			memoField.setValue(selectedTask.getMemo());
		}

		DatePicker sDateField = new DatePicker();
		sDateField.setValue(selectedTask.getsDate());
		sDateField.setLabel("開始日");
		DatePicker eDateField = new DatePicker();
		//eDateField.setValue(selectedTask.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		eDateField.setLabel("終了日");
		DatePicker deadlineField = new DatePicker();
		//deadlineField.setValue(selectedTask.getDeadline().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		deadlineField.setLabel("対応期限");


		Button editButton = new Button("更新",  event -> register(uid, selectedTask.getParentTaskId(), selectedCategory, titleField.getValue(), selectedStatus, memoField.getValue(), sDateField.getValue(), deadlineField.getValue()));
		format.add(titleField, pTaskSelect, categorySelect, statusSelect, memoField,sDateField, eDateField, deadlineField, editButton);
		dialog.add(format);
		dialog.open();

	}

	private void getTask(TaskForm task) {
		selectedTask = new TaskForm();
		selectedTask = task;
	}

	private void getCategory(CategoryEnums category) {
		selectedCategory = category.getId();
	}

	private void getStatus(StatusEnums status) {
		selectedStatus = status.getId();
	}

	private void buildQuickAddTaskFormat() {

	}

	private void register(long uid, long pTask, int category, String title, int status, String memo, LocalDate sDate, LocalDate deadline) {
		List<Object> paramList = Lists.newArrayList();
		paramList.add(uid);
		paramList.add(pTask);
		paramList.add(title);
		paramList.add(memo);
		paramList.add(status);
		paramList.add(category);
		paramList.add(sDate);
		paramList.add(null);
		paramList.add(deadline);

		taskService().add(paramList);
	}

	private void updateStatus(Dialog dialog, int status, List<TaskForm> targetTaskList) {}

	private void updateDeadline(Dialog dialog, LocalDate deadline, List<TaskForm> targetTaskList) {}

	private void deleteTasks(Dialog dialog, List<TaskForm> targetTaskList) {}


	@Override
	void construct(Component... UIParts) {
		// TODO 自動生成されたメソッド・スタブ

	}

	private TaskService taskService() {
		return new TaskService();
	}

}
