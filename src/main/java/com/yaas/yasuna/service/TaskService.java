package com.yaas.yasuna.service;

import java.util.List;

import com.google.common.collect.Lists;
import com.yaas.yasuna.converter.ConverterLogic;
import com.yaas.yasuna.converter.impl.TaskConverterLogicImpl;
import com.yaas.yasuna.dao.TaskDao;
import com.yaas.yasuna.entity.Task;
import com.yaas.yasuna.form.TaskForm;
import com.yaas.yasuna.transaction.Transaction;



public class TaskService {

	public List<TaskForm> getByUser(int userSeq) {

		List<Task> taskList = Lists.newArrayList();
		taskList = taskDao().getByUser(transaction().getConnection(), userSeq);

		List<TaskForm> taskFormList = Lists.newArrayList();

		for(Task task : taskList) {
			TaskForm taskForm = (TaskForm) converter().convertEntityToForm(task);
			taskFormList.add(taskForm);
		}

		return taskFormList;
	}

	private TaskDao taskDao() {
		return new TaskDao();
	}

	private ConverterLogic converter() {
		return new TaskConverterLogicImpl();
	}

	private Transaction transaction() {
		return new Transaction();
	}
}
