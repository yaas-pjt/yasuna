package com.yaas.yasuna.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.compress.utils.Lists;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.yaas.yasuna.consts.SessionAttributeConsts;
import com.yaas.yasuna.form.GridSettingForm;
import com.yaas.yasuna.form.QuickSettingForm;
import com.yaas.yasuna.form.TaskForm;
import com.yaas.yasuna.form.UserForm;
import com.yaas.yasuna.service.GridSettingService;
import com.yaas.yasuna.service.QuickSettingService;
import com.yaas.yasuna.service.TaskService;
import com.yaas.yasuna.ui.GridUIGenerator;
import com.yaas.yasuna.ui.impl.TaskGridUIGeneratorImpl;

@Route("mytask")
@PageTitle("mytask")
@CssImport("./styles/shared-styles.css")

public class PersonalTaskPage extends TemplatePage{

	private static final String CATEGORY_INBOX = "インボックス";
	private static final String CATEGORY_QUICK = "すぐやる";
	private static final String CATEGORY_ASK = "おねがい";
	private static final String CATEGORY_SOMEDAY = "次にやる";

	private UserForm loginUser;

	private List<TaskForm> primaryTaskList;
	private List<TaskForm> inboxTaskList;
	private List<TaskForm> quickTaskList;
	private List<TaskForm> askTaskList;
	private List<TaskForm> nextTaskList;

	private ComponentEventListener<GridDragStartEvent<TaskForm>> dragStartListener;
	private ComponentEventListener<GridDropEvent<TaskForm>> dropListener;
	private ComponentEventListener<GridDragEndEvent<TaskForm>> dragEndListener;

	private List<TaskForm> draggedTasks;
	private Grid<TaskForm> draggedGrid;

	private String oldCategory;
	private String newCategory;

	private GridSettingForm gridSettingForm;
	private QuickSettingForm quickSettingForm;

	private Grid<TaskForm> inboxGrid = new Grid<>(TaskForm.class);//インボックス用のグリッド
	private Grid<TaskForm> quickGrid = new Grid<>(TaskForm.class);//すぐやる用のグリッド
	private Grid<TaskForm> askGrid = new Grid<>(TaskForm.class);//おねがい用のグリッド
	private Grid<TaskForm> nextGrid = new Grid<>(TaskForm.class);//次にやる用のグリッド

	private static final long CHANGE_CATEGORY_MODE = 1;
	private static final long TRASH_MODE = 2;
	private static final long FINISH_MODE = 3;

	public PersonalTaskPage() {

		buildMainUI();
	}

	@Override
	public void buildMainUI() {


		Grid<TaskForm> baseGrid = new Grid<>(TaskForm.class);//ベースになるグリッド



		//ユーザー情報の取得
		getSessionAttribute();
		getUserSetting();

		generateTaskLists();//タスクリストを生成し、それぞれのListに振り分ける。

		//IDの付与とデータの登載
		inboxGrid.setItems(inboxTaskList);
		inboxGrid.setId("inbox");
		quickGrid.setItems(quickTaskList);
		quickGrid.setId("quick");
		askGrid.setItems(askTaskList);
		askGrid.setId("ask");
		nextGrid.setItems(nextTaskList);
		nextGrid.setId("next");

		//共通設定の取り込み
		gridUIGenerator().prepareBaseSetting(inboxGrid);
		gridUIGenerator().prepareHeader(gridSettingForm, inboxGrid);
		gridUIGenerator().prepareEditor(inboxGrid);
		gridUIGenerator().prepareBaseSetting(quickGrid);
		gridUIGenerator().prepareHeader(gridSettingForm, quickGrid);
		gridUIGenerator().prepareEditor(quickGrid);
		gridUIGenerator().prepareBaseSetting(askGrid);
		gridUIGenerator().prepareHeader(gridSettingForm, askGrid);
		gridUIGenerator().prepareEditor(askGrid);
		gridUIGenerator().prepareBaseSetting(nextGrid);
		gridUIGenerator().prepareHeader(gridSettingForm, nextGrid);
		gridUIGenerator().prepareEditor(nextGrid);

		Icon deleteIcon = new Icon(VaadinIcon.TRASH);
		Icon statusIcon = new Icon(VaadinIcon.REFRESH);
		Icon scheduleIcon = new Icon(VaadinIcon.CALENDAR);

		DropTarget<Icon> deleteDropTarget = DropTarget.create(deleteIcon);
		DropTarget<Icon> statusDropTarget = DropTarget.create(statusIcon);
		DropTarget<Icon> scheduleDropTarget = DropTarget.create(scheduleIcon);

		deleteDropTarget.addDropListener( event -> trashTasks());
		statusDropTarget.addDropListener(event -> changeStatus());
		scheduleDropTarget.addDropListener(event -> changeDeadline());


		setDragStartEvent(inboxGrid, quickGrid, askGrid, nextGrid);
		setDropEvent();
		setDragEndEvent(inboxGrid, quickGrid, askGrid, nextGrid);
		setDragAndDropEvent(inboxGrid, quickGrid, askGrid, nextGrid);

		//ボタンの生成
		Button addButton = new Button("追加", event -> addTask());
		Icon addButtonIcon = new Icon(VaadinIcon.PLUS);
		addButton.setIcon(addButtonIcon);
		addButton.addThemeVariants(ButtonVariant.LUMO_SMALL,
		        ButtonVariant.LUMO_PRIMARY);

		Button quickAddButton = new Button("すぐ追加", event -> addTaskQuickly());
		Icon quickAddButtonIcon = new Icon(VaadinIcon.FLASH);
		quickAddButton.setIcon(quickAddButtonIcon);
		quickAddButton.addThemeVariants(ButtonVariant.LUMO_SMALL,
		        ButtonVariant.LUMO_PRIMARY);

		Button refreshButton = new Button("更新", event ->open()) ;
		Icon refreshIcon = new Icon(VaadinIcon.REFRESH);
		refreshButton.setIcon(refreshIcon);
		refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL,
		        ButtonVariant.LUMO_PRIMARY);
		HorizontalLayout buttonGroup = new HorizontalLayout(addButton, quickAddButton);

		Component[] headerContents = new Component[] {addButton, quickAddButton, refreshButton};
		Component[] mainContents = new Component[] {inboxGrid, quickGrid, askGrid, nextGrid};
		Component[] footerContents = new Component[] {deleteIcon, statusIcon, scheduleIcon};

		construct(headerContents, mainContents, footerContents);

	}

	private List<TaskForm> generateTaskLists() {

		primaryTaskList = Lists.newArrayList();

		primaryTaskList = taskService().getByUser(loginUser.getSeq());
		divideTask(primaryTaskList);

		return primaryTaskList;
	}

	private void divideTask(List<TaskForm> primaryTaskList) {
		inboxTaskList  = Lists.newArrayList();
		quickTaskList = Lists.newArrayList();
		askTaskList = Lists.newArrayList();
		nextTaskList = Lists.newArrayList();


		for(TaskForm task : primaryTaskList) {
			switch(task.getCategory()) {

			case CATEGORY_INBOX:
				inboxTaskList.add(task);
				continue;

			case CATEGORY_QUICK:
				quickTaskList.add(task);
				continue;

			case CATEGORY_ASK:
				askTaskList.add(task);
				continue;

			case CATEGORY_SOMEDAY:
				nextTaskList.add(task);
				continue;

				default:
					break;
			}
		}
	}

	private void setDragStartEvent(Grid<TaskForm> inboxGrid, Grid<TaskForm> quickGrid, Grid<TaskForm> askGrid, Grid<TaskForm> nextGrid){
		dragStartListener = event -> {
			draggedTasks = event.getDraggedItems();
			draggedGrid = event.getSource();
			inboxGrid.setDropMode(GridDropMode.BETWEEN);
			quickGrid.setDropMode(GridDropMode.BETWEEN);
			askGrid.setDropMode(GridDropMode.BETWEEN);
			nextGrid.setDropMode(GridDropMode.BETWEEN);
		};
	}

	private void setDropEvent(){
		dropListener = event -> {


			   Optional<TaskForm> target = event.getDropTargetItem();
			    if (target.isPresent() && draggedTasks.contains(target.get())) {
			        return;
			    }


			    @SuppressWarnings("unchecked")
		ListDataProvider<TaskForm> sourceDataProvider = (ListDataProvider<TaskForm>) draggedGrid.getDataProvider();
			    List<TaskForm> sourceItems = new ArrayList<>(sourceDataProvider.getItems());
			    //sourceItems.removeAll(draggedTasks);
			  //  draggedGrid.setItems(sourceItems);

			    Grid<TaskForm> targetGrid = event.getSource();
			    @SuppressWarnings("unchecked")
			    ListDataProvider<TaskForm> targetDataProvider = (ListDataProvider<TaskForm>) targetGrid.getDataProvider();
			    List<TaskForm> targetItems = new ArrayList<>(targetDataProvider.getItems());
			    targetGrid.setItems(targetItems);

			    oldCategory = setCategorybyGrid(draggedGrid);
			    newCategory = setCategorybyGrid(targetGrid);
				    changeCategory(oldCategory, newCategory, draggedTasks);
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

	private void setDragEndEvent(Grid<TaskForm> inboxGrid, Grid<TaskForm> quickGrid, Grid<TaskForm> askGrid, Grid<TaskForm> nextGrid) {

		dragEndListener = event -> {
			draggedTasks = null;
			draggedGrid = null;
			inboxGrid.setDropMode(GridDropMode.BETWEEN);
			quickGrid.setDropMode(GridDropMode.BETWEEN);
			askGrid.setDropMode(GridDropMode.BETWEEN);
			nextGrid.setDropMode(GridDropMode.BETWEEN);
			generateTaskLists();
			inboxGrid.setItems(inboxTaskList);
			quickGrid.setItems(quickTaskList);
			askGrid.setItems(askTaskList);
			nextGrid.setItems(nextTaskList);
			inboxGrid.getDataProvider().refreshAll();
			quickGrid.getDataProvider().refreshAll();
			askGrid.getDataProvider().refreshAll();
			nextGrid.getDataProvider().refreshAll();

			};
		}

	private void setDragAndDropEvent(Grid<TaskForm> inboxGrid, Grid<TaskForm> quickGrid, Grid<TaskForm> askGrid, Grid<TaskForm> nextGrid) {

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

	private String setCategorybyGrid(Grid grid) {

		Optional<String> inboxOptional = Optional.of("inbox");
		Optional<String> quickOptional = Optional.of("quick");
		Optional<String> askOptional = Optional.of("ask");
		Optional<String> somedayOptional = Optional.of("next");

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


	private Button generateEditButton(Grid<TaskForm> grid, TaskForm selectedTask) {

		Button editButton = new Button("更新", clickEvent -> {
			PersonalTaskDialogPage dialog = new PersonalTaskDialogPage();

			dialog.editTask(loginUser.getSeq(), selectedTask, primaryTaskList);
		});
		return editButton;
	}

	private void addTask() {
		PersonalTaskDialogPage dialog = new PersonalTaskDialogPage();
		dialog.openAddTaskFormat(loginUser.getSeq(), primaryTaskList);
		init();
	}

	private void addTaskQuickly() {
		PersonalTaskDialogPage dialog = new PersonalTaskDialogPage();
		dialog.openQuickAddTaskDialog(loginUser.getSeq(), quickSettingForm);

	}

	private void changeCategory(String oldCategory, String newCategory, List<TaskForm> targetTaskList) {
		PersonalTaskDialogPage dialog = new PersonalTaskDialogPage();
		dialog.openChangeCategoryDialog(oldCategory, newCategory, targetTaskList);
		init();
	}

	private void changeStatus() {
		PersonalTaskDialogPage dialog = new PersonalTaskDialogPage();
		dialog.openChangeStatusDialog(draggedTasks);
		init();
	}

	private void changeDeadline() {
		PersonalTaskDialogPage dialog = new PersonalTaskDialogPage();
		dialog.openUpdateDeadlineDialog(draggedTasks);
		init();
	}

	private void trashTasks(List<TaskForm> taskList) {
		PersonalTaskDialogPage dialog = new PersonalTaskDialogPage();
		dialog.openDeleteTasksDialog(loginUser.getSeq(), taskList);
	}

	private void trashTasks() {
		PersonalTaskDialogPage dialog = new PersonalTaskDialogPage();
		dialog.openDeleteTasksDialog(loginUser.getSeq(), draggedTasks);
		init();
	}


	private void updateCategory(int category, List<TaskForm> tasks) {
		List<Long> seqList = Lists.newArrayList();

		for(TaskForm task : tasks) {
			seqList.add(task.getSeq());
			}

		//taskService().updateCategory(category, seqList);
		}

	public void init() {
		draggedTasks = null;
		draggedGrid = null;


	}


	public void notifyResult() {
		Notification notification = new Notification("処理が正常に完了しました。", 3000, Position.TOP_START);
		add(notification);
	}

	private void getSessionAttribute() {
		loginUser = new UserForm();
		loginUser =  (UserForm) VaadinService.getCurrentRequest().getWrappedSession().getAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_USER);
	}

	private void getUserSetting() {
		gridSettingForm = gridSettingService().getByUserSeq(loginUser.getSeq());
		quickSettingForm = quickSettingService().getByUserSeq(loginUser.getSeq());
	}


	private GridUIGenerator gridUIGenerator() {
		return new TaskGridUIGeneratorImpl();
	}

	private TaskService taskService() {
		return new TaskService();
	}

	private GridSettingService gridSettingService() {
		return new GridSettingService();
	}

	private QuickSettingService quickSettingService() {
		return new QuickSettingService();
	}

	private void open() {
		Dialog dialog = new Dialog();
		Button addButton = new Button("追加", event -> test(dialog));
		Icon addButtonIcon = new Icon(VaadinIcon.PLUS);
		addButton.setIcon(addButtonIcon);
		addButton.addThemeVariants(ButtonVariant.LUMO_SMALL,
		        ButtonVariant.LUMO_PRIMARY);
		dialog.add(addButton);
		dialog.open();
	}

	private void test(Dialog dialog) {
		TaskService taskService = new TaskService();
		List<Long> list = Lists.newArrayList();
		long test1 = 1004;
		list.add(test1);



		initAll();
		dialog.close();

	}

	private void initAll() {
		generateTaskLists();
		inboxGrid.setItems(inboxTaskList);
		quickGrid.setItems(quickTaskList);
		askGrid.setItems(askTaskList);
		nextGrid.setItems(nextTaskList);
		inboxGrid.getDataProvider().refreshAll();
		quickGrid.getDataProvider().refreshAll();
		askGrid.getDataProvider().refreshAll();
		nextGrid.getDataProvider().refreshAll();

	}
}
