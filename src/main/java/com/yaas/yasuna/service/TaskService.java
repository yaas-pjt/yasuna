package com.yaas.yasuna.service;

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

	public void add(List<Object> paramList) {
		taskDao().add(transaction().getConnection(), paramList);
	}

	public void updateCategory(int category, Date deadline, List<Long> seqList) {

		taskDao().updateCategory(transaction().getConnection(), category, deadline, seqList);
	}

	public boolean updateStatus(int status, List<Long> seqList) {

		if(RESULT_FAILURE == taskDao().updateStatus(transaction().getConnection(), status,  seqList)) {
			return false;
		} else {
			return true;
		}
	}

	public boolean updateDeadline(Date deadline, List<Long> seqList) {

		if(RESULT_FAILURE == taskDao().updateDeadline(transaction().getConnection(), deadline,  seqList)) {
			return false;
		} else {
			return true;
		}
	}

	public boolean deleteBySeq(List<Long> seqList) {

		if(RESULT_FAILURE == taskDao().deleteBySeq(transaction().getConnection(), seqList)) {
			return false;
		} else {
			return true;
		}
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
