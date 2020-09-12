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

	private int RESULT_FAILURE = 0;

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

	public boolean add(List<Object> paramList) {

		if(RESULT_FAILURE == taskDao().add(transaction().getConnection(), paramList)) {
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
