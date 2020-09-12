package com.yaas.yasuna.view;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dnd.GridDragEndEvent;
import com.vaadin.flow.component.grid.dnd.GridDragStartEvent;
import com.vaadin.flow.component.grid.dnd.GridDropEvent;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.yaas.yasuna.consts.CategoryConsts;
import com.yaas.yasuna.consts.SessionAttributeConsts;
import com.yaas.yasuna.enums.CategoryEnums;
import com.yaas.yasuna.enums.GridHeaderEnums;
import com.yaas.yasuna.enums.GridIdEnums;
import com.yaas.yasuna.enums.StatusEnums;
import com.yaas.yasuna.exception.YasunaException;
import com.yaas.yasuna.form.GridSettingForm;
import com.yaas.yasuna.form.QuickSettingForm;
import com.yaas.yasuna.form.TaskForm;
import com.yaas.yasuna.form.UserForm;
import com.yaas.yasuna.service.TaskService;
import com.yaas.yasuna.ui.GridUIGenerator;
import com.yaas.yasuna.ui.impl.TaskGridUIGeneratorImpl;

@Route("personaltask")
@PageTitle("personaltask")
@CssImport("./styles/shared-styles.css")
public class PersonalTaskView extends CommonView {

	private UserForm loginUser;
	private GridSettingForm gridSetting;
	private QuickSettingForm quickSetting;

	private List<TaskForm> inboxTaskList;
	private List<TaskForm> quickTaskList;
	private List<TaskForm> askTaskList;
	private List<TaskForm> nextTaskList;

	private ComponentEventListener<GridDragStartEvent<TaskForm>> dragStartListener;
	private ComponentEventListener<GridDropEvent<TaskForm>> dropListener;
	private ComponentEventListener<GridDragEndEvent<TaskForm>> dragEndListener;

	private List<TaskForm> draggedTasks;
	private Grid<TaskForm> draggedGrid;

	private String presentCategory;
	private String newCategory;

	private Grid<TaskForm> inboxGrid;//インボックス用のグリッド
	private Grid<TaskForm> quickGrid;//すぐやる用のグリッド
	private Grid<TaskForm> askGrid;//おねがい用のグリッド
	private Grid<TaskForm> nextGrid;//次にやる用のグリッド

	private Dialog commonDialog;

	private int selectedCategory;
	private int selectedStatus;

	public PersonalTaskView() {
		buildMainUI();
	}

	@Override
	public void buildMainUI() {

		//Gridの宣言
		inboxGrid = new Grid<>(TaskForm.class);
		quickGrid = new Grid<>(TaskForm.class);
		askGrid = new Grid<>(TaskForm.class);
		nextGrid = new Grid<>(TaskForm.class);


		//ログインユーザーの情報取得
		getSessionAttributes();
		initGrid();

		//共通設定
		setGridSetting();

		//ドラッグアンドドロップのイベント設定
		prepareDragAndDropFuntion();

		Component[] mainContents = new Component[] {inboxGrid, quickGrid, askGrid, nextGrid};

		construct(generateHeaderContents(), mainContents, generateFooterContents());
	}

	private void prepareDragAndDropFuntion() {
		setDragStartEvent();
		setDropEvent();
		setDragEndEvent();
		setDragAndDropEvent();
	}

	private void setDragStartEvent() {
		dragStartListener = event -> {
			draggedTasks = event.getDraggedItems();
			draggedGrid = event.getSource();
			inboxGrid.setDropMode(GridDropMode.BETWEEN);
			quickGrid.setDropMode(GridDropMode.BETWEEN);
			askGrid.setDropMode(GridDropMode.BETWEEN);
			nextGrid.setDropMode(GridDropMode.BETWEEN);
		};
	}

	private void setDropEvent() {

		dropListener = event -> {
			Optional<TaskForm> target = event.getDropTargetItem();
			if (target.isPresent() && draggedTasks.contains(target.get())) {
				return;
				}

			@SuppressWarnings("unchecked")
			ListDataProvider<TaskForm> sourceDataProvider = (ListDataProvider<TaskForm>) draggedGrid.getDataProvider();
			List<TaskForm> sourceItems = new ArrayList<>(sourceDataProvider.getItems());

			Grid<TaskForm> targetGrid = event.getSource();
			@SuppressWarnings("unchecked")
			ListDataProvider<TaskForm> targetDataProvider = (ListDataProvider<TaskForm>) targetGrid.getDataProvider();
			List<TaskForm> targetItems = new ArrayList<>(targetDataProvider.getItems());
			targetGrid.setItems(targetItems);


			presentCategory = setCategorybyGrid(draggedGrid);
			newCategory = setCategorybyGrid(targetGrid);
			openUpdateCategoryDialog(presentCategory, newCategory, draggedTasks);
			inboxGrid.getDataProvider().refreshAll();
			quickGrid.getDataProvider().refreshAll();
			askGrid.getDataProvider().refreshAll();
			nextGrid.getDataProvider().refreshAll();
			};

			ComponentEventListener<GridDragEndEvent<TaskForm>> dragEndListener = event -> {
				draggedTasks = null;
				draggedGrid = null;
				inboxGrid.setDropMode(GridDropMode.BETWEEN);
				quickGrid.setDropMode(GridDropMode.BETWEEN);
				askGrid.setDropMode(GridDropMode.BETWEEN);
				nextGrid.setDropMode(GridDropMode.BETWEEN);
			};
		}

	private String setCategorybyGrid(Grid grid) {

		Optional<String> inboxOptional = Optional.of(GridIdEnums.TaskGridIdEnum.INBOX.getLabel());
		Optional<String> quickOptional = Optional.of(GridIdEnums.TaskGridIdEnum.QUICK.getLabel());
		Optional<String> askOptional = Optional.of(GridIdEnums.TaskGridIdEnum.ASK.getLabel());
		Optional<String> nextOptional = Optional.of(GridIdEnums.TaskGridIdEnum.NEXT.getLabel());

		if(inboxOptional.equals(grid.getId())) {
			return GridHeaderEnums.TaskGridHeaderEnum.INBOX.getLabel();
		}else if(quickOptional.equals(grid.getId())) {
			return GridHeaderEnums.TaskGridHeaderEnum.QUICK.getLabel();
		}else if(askOptional.equals(grid.getId())) {
			return GridHeaderEnums.TaskGridHeaderEnum.ASK.getLabel();
		}else if(nextOptional.equals(grid.getId())) {
			return GridHeaderEnums.TaskGridHeaderEnum.NEXT.getLabel();
		}else {
			return null;
		}
	}

	private void setDragEndEvent() {
		dragEndListener = event -> {
			draggedTasks = null;
			draggedGrid = null;
			inboxGrid.setDropMode(GridDropMode.BETWEEN);
			quickGrid.setDropMode(GridDropMode.BETWEEN);
			askGrid.setDropMode(GridDropMode.BETWEEN);
			nextGrid.setDropMode(GridDropMode.BETWEEN);
			};
		}

	private void setDragAndDropEvent() {
		inboxGrid.setSelectionMode(Grid.SelectionMode.MULTI);
		inboxGrid.addDropListener(dropListener);
		inboxGrid.addDragStartListener(dragStartListener);
		inboxGrid.addDragEndListener(dragEndListener);
		inboxGrid.setRowsDraggable(true);

		quickGrid.setSelectionMode(Grid.SelectionMode.MULTI);
		quickGrid.addDropListener(dropListener);
		quickGrid.addDragStartListener(dragStartListener);
		quickGrid.addDragEndListener(dragEndListener);
		quickGrid.setRowsDraggable(true);

		askGrid.setSelectionMode(Grid.SelectionMode.MULTI);
		askGrid.addDropListener(dropListener);
		askGrid.addDragStartListener(dragStartListener);
		askGrid.addDragEndListener(dragEndListener);
		askGrid.setRowsDraggable(true);

		nextGrid.setSelectionMode(Grid.SelectionMode.MULTI);
		nextGrid.addDropListener(dropListener);
		nextGrid.addDragStartListener(dragStartListener);
		nextGrid.addDragEndListener(dragEndListener);
		nextGrid.setRowsDraggable(true);
	}

	private Component[] generateHeaderContents() {

		Button addButton = new Button("追加", event -> openAddDialog());
		Icon addButtonIcon = new Icon(VaadinIcon.PLUS);
		addButton.setIcon(addButtonIcon);
		addButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);

		return new Component[] {addButton};
	}




	private Component[] generateFooterContents() {

		Icon deleteIcon = new Icon(VaadinIcon.TRASH);
		Icon statusIcon = new Icon(VaadinIcon.REFRESH);
		Icon scheduleIcon = new Icon(VaadinIcon.CALENDAR);

		DropTarget<Icon> deleteDropTarget = DropTarget.create(deleteIcon);
		DropTarget<Icon> statusDropTarget = DropTarget.create(statusIcon);
		DropTarget<Icon> scheduleDropTarget = DropTarget.create(scheduleIcon);

		deleteDropTarget.addDropListener( event -> openDeleteDialog(draggedTasks));
		statusDropTarget.addDropListener(event -> openChangeStatusDialog(draggedTasks));
		scheduleDropTarget.addDropListener(event -> openChangeDeadlineDialog(draggedTasks));

		return new Component[] {deleteIcon, statusIcon, scheduleIcon};
	}

	private void getSessionAttributes() {
		loginUser = (UserForm) VaadinService.getCurrentRequest().getWrappedSession().getAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_USER);
		gridSetting = (GridSettingForm) VaadinService.getCurrentRequest().getWrappedSession().getAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_GRID_SETTING);
		quickSetting = (QuickSettingForm) VaadinService.getCurrentRequest().getWrappedSession().getAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_QUICK_SETTING);
	}

	private void initGrid() {
		getTasks();
		setData();
	}

	private void getTasks() {
		List<TaskForm> allTaskList = taskService().getByUser(loginUser.getSeq());
		inboxTaskList = Lists.newArrayList();
		quickTaskList = Lists.newArrayList();
		askTaskList = Lists.newArrayList();
		nextTaskList = Lists.newArrayList();

		for(TaskForm task : allTaskList) {

			switch(task.getCategory()) {

			case CategoryConsts.CATEGORY_INBOX :
				inboxTaskList.add(task);
				continue;

			case CategoryConsts.CATEGORY_QUICK :
				quickTaskList.add(task);
				continue;

			case CategoryConsts.CATEGORY_ASK :
				askTaskList.add(task);
				continue;

			case CategoryConsts.CATEGORY_NEXT :
				nextTaskList.add(task);
				continue;
			}
		}
	}

	private void setData() {
		inboxGrid.setItems(inboxTaskList);
		inboxGrid.setId("inbox");
		quickGrid.setItems(quickTaskList);
		quickGrid.setId("quick");
		askGrid.setItems(askTaskList);
		askGrid.setId("ask");
		nextGrid.setItems(nextTaskList);
		nextGrid.setId("next");
	}

	private void setGridSetting() {
		gridUIGenerator().prepareBaseSetting(inboxGrid);
		gridUIGenerator().prepareHeader(gridSetting, inboxGrid);
		gridUIGenerator().prepareEditor(inboxGrid);
		gridUIGenerator().prepareBaseSetting(quickGrid);
		gridUIGenerator().prepareHeader(gridSetting, quickGrid);
		gridUIGenerator().prepareEditor(quickGrid);
		gridUIGenerator().prepareBaseSetting(askGrid);
		gridUIGenerator().prepareHeader(gridSetting, askGrid);
		gridUIGenerator().prepareEditor(askGrid);
		gridUIGenerator().prepareBaseSetting(nextGrid);
		gridUIGenerator().prepareHeader(gridSetting, nextGrid);
		gridUIGenerator().prepareEditor(nextGrid);
	}

	private void prepareDialog() {
		commonDialog = new Dialog();
		setCommonDialogSetting(commonDialog);
	}

	private void openAddDialog() {
		VerticalLayout format = new VerticalLayout();
		TextField titleField = new TextField();
		titleField.setLabel("タイトル");

		TextArea memoField = new TextArea("メモ");

		//デフォルト設定から登録値を生成
		long defaultParentTaskId = 0;
		int defaultCategory = quickSetting.getCategorySetting();
		int defaultStatus = quickSetting.getStatusSetting();
		LocalDate defaultsDate  = LocalDate.now().plusDays(quickSetting.getsDateSetting());
		LocalDate defaultDeadline  = LocalDate.now().plusDays(quickSetting.getDeadlineSetting());

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

		Button registerButton = new Button("追加",  event -> addTask(loginUser.getSeq(), defaultParentTaskId, defaultCategory, titleField.getValue(), defaultStatus, memoField.getValue(), defaultsDate, defaultDeadline));
		format.add(titleField, categorySelect, statusSelect, memoField, sDateField,  deadlineField, registerButton);

		prepareDialog();
		commonDialog.add(format);
		commonDialog.open();
	}
	private void addTask(long uid, long pTask, int category, String title, int status, String memo, LocalDate sDate, LocalDate deadline) {
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

		if(taskService().add(paramList)) {
			commonDialog.close();

		} else {
			throw new YasunaException("タスク追加に失敗しました。");
		}
	}

	private void getCategory(CategoryEnums category) {
		selectedCategory = category.getId();
	}

	private void getStatus(StatusEnums status) {
		selectedStatus = status.getId();
	}


	private void openUpdateCategoryDialog(String presentCategory, String newCategory, List<TaskForm> targetTaskList){

		prepareDialog();
		commonDialog.add(new Text("タスクの分類が【" + presentCategory + "】から【" + newCategory +  "】に更新されます。対応期限を再設定してください。"));

		VerticalLayout format = new VerticalLayout();

		DatePicker deadlineField = new DatePicker();
		deadlineField.setValue(LocalDate.now());
		deadlineField.setLabel("対応期限");

		Button changeButton = new Button("更新",  event -> updateCategory(newCategory, deadlineField.getValue(), targetTaskList));

		format.add(deadlineField, changeButton);
		commonDialog.add(format);
		commonDialog.open();

	}

	private void updateCategory(String newCategory, LocalDate deadline, List<TaskForm> targetTaskList) {
		int category = convertCategory(newCategory);
		Date deadlineDate = Date.from(deadline.atStartOfDay(ZoneId.systemDefault()).toInstant());

		if(taskService().updateCategory(category, deadlineDate, targetTaskList)) {
			commonDialog.close();
		} else {
			throw new YasunaException("分類の更新に失敗しました。");
		}
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

	private void openDeleteDialog(List<TaskForm> targetTaskList) {
		Grid<TaskForm> targetGrid = new Grid<>(TaskForm.class);
		targetGrid.removeAllColumns();
		targetGrid.addColumn(TaskForm::getTitle).setHeader("タイトル");
		targetGrid.setItems(targetTaskList);

		Button deleteButton = new Button("削除",  event -> deleteTasks(targetTaskList));

		prepareDialog();
		commonDialog.add(new Text("選択したタスクを削除します。よろしいですか？"));
		commonDialog.add(targetGrid);
		commonDialog.add(deleteButton);
		commonDialog.open();

	}

	private void deleteTasks(List<TaskForm> targetTaskList) {
		if(taskService().deleteBySeq(targetTaskList)) {
			commonDialog.close();
		} else {
			throw new YasunaException("タスクの削除に失敗しました。");
		}
	}

	private void openChangeStatusDialog(List<TaskForm> targetTaskList) {
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

		Button updateButton = new Button("更新",  event -> updateStatus(selectedStatus, targetTaskList));

		prepareDialog();
		commonDialog.add(new Text("選択したタスクのステータスを一括更新します。"));
		commonDialog.add(targetGrid);
		commonDialog.add(statusSelect);
		commonDialog.add(updateButton);
		commonDialog.open();
	}

	private void updateStatus(int status, List<TaskForm> targetTaskList) {
		if(taskService().updateStatus(status, targetTaskList)) {
			commonDialog.close();
		} else {
			throw new YasunaException("ステータス更新に失敗しました。");
		}
	}

	private void openChangeDeadlineDialog(List<TaskForm> targetTaskList) {
		Grid<TaskForm> targetGrid = new Grid<>(TaskForm.class);
		targetGrid.removeAllColumns();
		targetGrid.addColumn(TaskForm::getTitle).setHeader("タイトル");
		targetGrid.addColumn(TaskForm::getDeadline).setHeader("対応期限");
		targetGrid.setItems(targetTaskList);

		DatePicker deadlineField = new DatePicker();
		LocalDate now = LocalDate.now();
		deadlineField.setValue(now);
		deadlineField.setLabel("対応期限");

		Button updateButton = new Button("更新",  event -> updateDeadline(deadlineField.getValue(), targetTaskList));
		prepareDialog();
		commonDialog.add(new Text("選択したタスクの対応期限を一括更新します。"));
		commonDialog.add(targetGrid);
		commonDialog.add(deadlineField);
		commonDialog.add(updateButton);
		commonDialog.open();
	}

	private void updateDeadline(LocalDate deadline, List<TaskForm> targetTaskList) {
		if(taskService().updateDeadline(deadline, targetTaskList)) {
			commonDialog.close();
			initGrid();
			announce();
		} else {
			throw new YasunaException("対応期限の更新に失敗しました。");
		}
	}

	private void announce() {
		Notification notification = new Notification();
		notification.add("処理が完了しました。");
		notification.setPosition(Position.TOP_CENTER);
		notification.setDuration(3000);
		notification.open();

	}

	private GridUIGenerator<TaskForm> gridUIGenerator() {
		return new TaskGridUIGeneratorImpl();
	}

	private TaskService taskService() {
		return new TaskService();
	}
}
