package com.yaas.yasuna.converter.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.yaas.yasuna.converter.ConverterLogic;
import com.yaas.yasuna.entity.Task;
import com.yaas.yasuna.form.TaskForm;

public class TaskConverterLogicImpl implements ConverterLogic<Task, TaskForm> {

	@Override
	public TaskForm convertEntityToForm(Task task) {
		TaskForm taskForm = new TaskForm();

		taskForm.setSeq(task.getSeq());
		taskForm.setFkUserSeq(task.getFkUserSeq());
		taskForm.setParentTaskId(task.getParentTaskId());
		taskForm.setTitle(task.getTitle());
		taskForm.setStatus(convertStatusToForm(task.getStatus()));
		taskForm.setMemo(task.getMemo());
		taskForm.setCategory(convertCategoryToForm(task.getCategory()));
		taskForm.setsDate(convertToLocalDate(task.getsDate()));
		taskForm.seteDate(convertToLocalDate(task.geteDate()));
		taskForm.setDeadline(convertToLocalDate(task.getDeadline()));

		return taskForm;
	}

	private LocalDate convertToLocalDate(Date date) {

		if(date == null) {
			Date d = new Date();

			return Instant.ofEpochMilli(d.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		}

		    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	private String convertStatusToForm(int status) {

		switch(status) {

		case 0:

			return "未着手";

		case 1:

			return "着手中";

		case 2:

			return "もうすぐ終わる";

		case 3:

			return "課題あり";

		case 4:

			return "要救助";

		case 9:

			return "完了";

		default :

			return "異常ステータス";
		}
	}

	private String convertCategoryToForm(int category) {

		switch(category) {

		case 1:

			return "インボックス";

		case 2:

			return "すぐやる";

		case 3:

			return "おねがい";

		case 4:

			return "次にやる";

		default :

			return "ゴミ";
		}
	}


	@Override
	public Task convertFormToEntity(TaskForm taskForm) {
		Task task = new Task();

		task.setSeq(taskForm.getSeq());
		task.setFkUserSeq(taskForm.getFkUserSeq());
		task.setParentTaskId(taskForm.getParentTaskId());
		task.setTitle(taskForm.getTitle());
		task.setStatus(convertStatusToEntity(taskForm.getStatus()));
		task.setMemo(taskForm.getMemo());
		task.setCategory(convertCategoryToEntity(taskForm.getCategory()));
		task.setsDate(convertToDate(taskForm.getsDate()));
		task.seteDate(convertToDate(taskForm.geteDate()));
		task.setDeadline(convertToDate(taskForm.getDeadline()));
		task.setPrcDate(task.getPrcDate());

		return task;
	}

	private int convertStatusToEntity(String status) {

		switch(status) {

		case "未着手":

			return 0;

		case "着手中":

			return 1;

		case "もうすぐ終わる":

			return 2;

		case "課題あり":

			return 3;

		case "要救助":

			return 4;

		case "完了":

			return 9;

		default :

			return -1000;
		}
	}

	private int convertCategoryToEntity(String category) {

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

	private Date convertToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

}
