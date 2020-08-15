package com.yaas.yasuna.converter.impl;

import com.yaas.yasuna.converter.ConverterLogic;
import com.yaas.yasuna.entity.Task;
import com.yaas.yasuna.form.TaskForm;

public class TaskConverterLogicImpl implements ConverterLogic<Task, TaskForm> {

	@Override
	public TaskForm convertEntityToForm(Task task) {
		TaskForm taskForm = new TaskForm();

		taskForm.setSeq(task.getSeq());
		taskForm.setUid(task.getUid());
		taskForm.setTaskId(task.getTaskId());
		taskForm.setParentId(task.getParentId());
		taskForm.setTitle(task.getTitle());
		taskForm.setStatus(task.getStatus());
		taskForm.setMemo(task.getMemo());
		taskForm.setStartDate(task.getStartDate());
		taskForm.setEndDate(task.getEndDate());
		taskForm.setDeadline(task.getDeadline());
		taskForm.setPrcDate(task.getPrcDate());

		return taskForm;
	}

	@Override
	public Task convertFormToEntity(TaskForm taskForm) {
		// TODO 自動生成されたメソッド・ス fタブ
		return null;
	}

}
