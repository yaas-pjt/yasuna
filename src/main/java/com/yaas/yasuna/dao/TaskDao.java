package com.yaas.yasuna.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.yaas.yasuna.entity.Task;
import com.yaas.yasuna.entity.map.TaskMap;

public class TaskDao extends AbsDao<Task> {

	private TaskMap taskMap = new TaskMap();


	public List<Task> getByUser(Connection connection, long fkUserSeq) {
		Task task = new Task();

		List<Object> paramList = Lists.newArrayList();
		paramList.add(fkUserSeq);

		return (List<Task>) getByConditions(task, taskMap.getMap(), connection, GET_BY_SEQ, paramList);
	}

	public int add(Connection connection, List<Object> paramList) {

		return add(connection, ADD, paramList);
	}

	public int update(Connection connection, List<Object> paramList) {
		return update(connection, SIMPLE_UPDATE, paramList);
	}

	public int updateCategory(Connection connection, int category, Date deadline, List<Task> taskList) {

		List<Object> paramList = Lists.newArrayList();

		paramList.add(category);
		paramList.add(deadline);

		String inParams = "";

		for(Task task : taskList) {
			paramList.add(task.getSeq());
			inParams = inParams + ",?";
		}

		StringBuilder stringBuilder = new StringBuilder(inParams);
		stringBuilder.deleteCharAt(0);

		String updateSQL = UPDATE_CATEGORY + stringBuilder + ")";

		int updateRows = update(connection, updateSQL, paramList);

		return updateRows;
	}

	public int updateStatus(Connection connection, int status, List<Task> taskList) {
		List<Object> paramList = Lists.newArrayList();
		String inParams = "";

		paramList.add(status);

		for(Task task : taskList) {
			paramList.add(task.getSeq());
			inParams = inParams + ",?";
		}
		StringBuilder stringBuilder = new StringBuilder(inParams);
		stringBuilder.deleteCharAt(0);

		String deleteSQL =  UPDATE_STATUS + stringBuilder + ")";

		int updateRows = delete(connection, deleteSQL, paramList);

		return updateRows;

	}

	public int updateDeadline(Connection connection, Date deadline, List<Task> taskList) {

		List<Object> paramList = Lists.newArrayList();
		String inParams = "";

		paramList.add(deadline);

		for(Task task : taskList) {
			paramList.add(task.getSeq());
			inParams = inParams + ",?";
		}

		StringBuilder stringBuilder = new StringBuilder(inParams);
		stringBuilder.deleteCharAt(0);

		String deleteSQL =  UPDATE_DEADLINE + stringBuilder + ")";

		int updateRows = delete(connection, deleteSQL, paramList);

		return updateRows;

	}


	public int deleteBySeq(Connection connection, List<Task> taskList) {

		List<Object> paramList = Lists.newArrayList();
		String inParams = "";

		for(Task task : taskList) {
			paramList.add(task.getSeq());
			inParams = inParams + ",?";
		}
		StringBuilder stringBuilder = new StringBuilder(inParams);
		stringBuilder.deleteCharAt(0);

		String deleteSQL =  DELETE_BY_SEQ + stringBuilder + ")";

		int updateRows = delete(connection, deleteSQL, paramList);

		return updateRows;
	}

	private static final String GET_BY_SEQ = "SELECT * FROM t_task WHERE FK_USER_SEQ = ? ORDER BY CATEGORY";
	private static final String ADD = "INSERT INTO t_task (FK_USER_SEQ, PARENT_TASK_SEQ, TITLE, MEMO, STATUS, CATEGORY, SDATE, EDATE, DEADLINE) VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String SIMPLE_UPDATE = "UPDATE t_task SET TITLE = ?, MEMO = ?, STATUS = ?, SDATE = ?, EDATE = ?, DEADLINE = ? WHERE SEQ = ?";
	private static final String UPDATE_CATEGORY = "UPDATE t_task SET CATEGORY = ?, DEADLINE = ? WHERE SEQ IN (";
	private static final String UPDATE_STATUS = "UPDATE t_task SET STATUS = ? WHERE SEQ IN (";
	private static final String UPDATE_DEADLINE = "UPDATE t_task SET DEADLINE = ? WHERE SEQ IN (";
	private static final String DELETE_BY_SEQ = "DELETE FROM t_task WHERE SEQ IN (";

}
