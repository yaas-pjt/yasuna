package com.yaas.yasuna.page;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.yaas.yasuna.consts.SessionAttributeConsts;
import com.yaas.yasuna.form.TaskForm;
import com.yaas.yasuna.form.UserForm;
import com.yaas.yasuna.service.TaskService;

@Route("mytask")
@PageTitle("mytask")
@CssImport("./styles/shared-styles.css")

public class PersonalTaskPage extends TemplatePage{

	private static final String HEADER_TITLE = "タイトル";
	private static final String HEADER_STATUS = "ステータス";
	private static final String HEADER_MEMO = "メモ";
	private static final String HEADER_STARTDATE = "開始日";
	private static final String HEADER_ENDDATE = "終了日";
	private static final String HEADER_DEADLINE = "対応期限";

	private UserForm loginUser;

	public PersonalTaskPage() {
		buildUI();
	}

	@Override
	public void buildUI() {
		setSizeFull();
		setClassName("login-screen");

		//タスクリスト本体
		Grid<TaskForm> grid = new Grid<>(TaskForm.class);
		grid.setItems(generateTaskList());
		grid.removeAllColumns();
		grid.addColumn(TaskForm::getTitle).setHeader(HEADER_TITLE);
		grid.addColumn(TaskForm::getStatus).setHeader(HEADER_STATUS);
		grid.addColumn(TaskForm::getMemo).setHeader(HEADER_MEMO);
		grid.addColumn(TaskForm::getStartDate).setHeader(HEADER_STARTDATE);
		grid.addColumn(TaskForm::getEndDate).setHeader(HEADER_ENDDATE);
		grid.addColumn(TaskForm::getDeadline).setHeader(HEADER_DEADLINE);

		//要素を詰め込む
		construct(grid);
	}

	@Override
	void construct(Component... UIParts) {
		buildHeader();
		add(UIParts);
		buildFooter();

	}

	private List<TaskForm> generateTaskList() {
		List<TaskForm> personalTaskList = Lists.newArrayList();

		getSessionAttribute();

		personalTaskList = taskService().getByUser(loginUser.getSeq());


		return personalTaskList;
	}

	private void getSessionAttribute() {
		loginUser = new UserForm();
		loginUser =  (UserForm) VaadinService.getCurrentRequest().getWrappedSession().getAttribute(SessionAttributeConsts.SESSION_ATTRIBUTE_USER);
	}

	public TaskService taskService() {
		return new TaskService();
	}
}
