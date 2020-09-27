package com.yaas.yasuna.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.yaas.yasuna.converter.ConverterLogic;
import com.yaas.yasuna.converter.impl.TaskConverterLogicImpl;
import com.yaas.yasuna.dao.TaskDao;
import com.yaas.yasuna.entity.Task;
import com.yaas.yasuna.form.TaskForm;
import com.yaas.yasuna.transaction.Transaction;



public class TaskService {

	private static final int RESULT_FAILURE = 0;

	public List<TaskForm> getByUser(long fkUserSeq) {

		List<Task> taskList = Lists.newArrayList();
		taskList = taskDao().getByUser(transaction().getConnection(), fkUserSeq);

		List<TaskForm> taskFormList = Lists.newArrayList();

		for(Task task : taskList) {
			TaskForm taskForm = (TaskForm) converter().convertEntityToForm(task);
			taskFormList.add(taskForm);
		}

		return taskFormList;
	}

	public boolean add(long uid, long pTask, int category, String title, int status, String memo, LocalDate sDate, LocalDate deadline) {
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
		if(RESULT_FAILURE == taskDao().add(transaction().getConnection(), paramList)) {
			return false;
		} else {
			return true;
		}
	}

	public boolean update(String title, int status, String memo, Date sDate, Date eDate, Date deadline, long seq) {
		List<Object> paramList = Lists.newArrayList();

		paramList.add(title);
		paramList.add(memo);
		paramList.add(status);
		paramList.add(sDate);
		paramList.add(eDate);
		paramList.add(deadline);
		paramList.add(seq);

		if(RESULT_FAILURE == taskDao().update(transaction().getConnection(), paramList)) {
			return false;
		} else {
			return true;
		}
	}

	public boolean updateCategory(int category, Date deadline, List<TaskForm> taskFormList) {

		List<Task> taskList = generateTaskList(taskFormList);
		if(taskFormList.size() == taskDao().updateCategory(transaction().getConnection(), category, deadline, taskList)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateStatus(int status, List<TaskForm> taskFormList) {
		List<Task> taskList = generateTaskList(taskFormList);

		if(taskFormList.size() == taskDao().updateStatus(transaction().getConnection(), status,  taskList)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateDeadline(LocalDate deadline, List<TaskForm> taskFormList) {
		Date deadlineDate = Date.from(deadline.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<Task> taskList = generateTaskList(taskFormList);

		if(taskFormList.size() == taskDao().updateDeadline(transaction().getConnection(), deadlineDate,  taskList)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteBySeq(List<TaskForm> taskFormList) {
		List<Task> taskList = generateTaskList(taskFormList);


		if(taskFormList.size() == taskDao().deleteBySeq(transaction().getConnection(), taskList)) {
			return true;
		} else {
			return false;
		}
	}

	private List<Task> generateTaskList(List<TaskForm> taskFormList){
		List<Task> taskList = Lists.newArrayList();

		for(TaskForm task : taskFormList) {
			taskList.add(converter().convertFormToEntity(task));
		}
		return taskList;
	}

	private TaskDao taskDao() {
		return new TaskDao();
	}

	private ConverterLogic<Task, TaskForm> converter() {
		return new TaskConverterLogicImpl();
	}

	private Transaction transaction() {
		return new Transaction();
	}
}
