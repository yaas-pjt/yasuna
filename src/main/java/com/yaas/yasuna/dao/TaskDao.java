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

	public void add(Connection connection, List<Object> paramList) {
		Task task = new Task();

		add(connection, ADD, paramList);
	}

	public void updateCategory(Connection connection, int category, Date deadline, List<Long> seqList) {

		List<Object> paramList = Lists.newArrayList();

		paramList.add(category);
		paramList.add(deadline);

		String inParams = "";

		for(long seq : seqList) {
			paramList.add(seq);
			inParams = inParams + ",?";
		}
		StringBuilder stringBuilder = new StringBuilder(inParams);
		stringBuilder.deleteCharAt(0);

		String updateSQL = UPDATE_CATEGORY + stringBuilder + ")";


		update(connection, updateSQL, paramList);
	}

	public int updateStatus(Connection connection, int status, List<Long> seqList) {
		List<Object> paramList = Lists.newArrayList();
		String inParams = "";

		paramList.add(status);

		for(long seq : seqList) {
			paramList.add(seq);
			inParams = inParams + ",?";
		}
		StringBuilder stringBuilder = new StringBuilder(inParams);
		stringBuilder.deleteCharAt(0);

		String deleteSQL =  UPDATE_STATUS + stringBuilder + ")";

		int updateRows = delete(connection, deleteSQL, paramList);

		return updateRows;

	}

	public int updateDeadline(Connection connection, Date deadline, List<Long> seqList) {

		List<Object> paramList = Lists.newArrayList();
		String inParams = "";

		paramList.add(deadline);

		for(long seq : seqList) {
			paramList.add(seq);
			inParams = inParams + ",?";
		}
		StringBuilder stringBuilder = new StringBuilder(inParams);
		stringBuilder.deleteCharAt(0);

		String deleteSQL =  UPDATE_DEADLINE + stringBuilder + ")";

		int updateRows = delete(connection, deleteSQL, paramList);

		return updateRows;

	}


	public int deleteBySeq(Connection connection, List<Long> seqList) {

		List<Object> paramList = Lists.newArrayList();
		String inParams = "";

		for(long seq : seqList) {
			paramList.add(seq);
			inParams = inParams + ",?";
		}
		StringBuilder stringBuilder = new StringBuilder(inParams);
		stringBuilder.deleteCharAt(0);

		String deleteSQL =  DELETE_BY_SEQ + stringBuilder + ")";

		int updateRows = delete(connection, deleteSQL, paramList);

		return updateRows;
	}

	private static final String GET_BY_SEQ = "SELECT * FROM T_TASK WHERE FK_USER_SEQ = ?";
	private static final String ADD = "INSERT INTO T_TASK (FK_USER_SEQ, PARENT_TASK_SEQ, TITLE, MEMO, STATUS, CATEGORY, SDATE, EDATE, DEADLINE) VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_CATEGORY = "UPDATE T_TASK SET CATEGORY = ?, DEADLINE = ? WHERE SEQ IN (";
	private static final String UPDATE_STATUS = "UPDATE T_TASK SET STATUS = ? WHERE SEQ IN (";
	private static final String UPDATE_DEADLINE = "UPDATE T_TASK SET DEADLINE = ? WHERE SEQ IN (";
	private static final String DELETE_BY_SEQ = "DELETE FROM T_TASK WHERE SEQ IN (";

}
