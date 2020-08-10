package com.yaas.yasuna.dao;

import java.sql.Connection;
import java.util.List;

import com.google.common.collect.Lists;
import com.yaas.yasuna.entity.Task;
import com.yaas.yasuna.entity.map.TaskMap;

public class TaskDao extends AbsDao<Task> {

	private TaskMap taskMap = new TaskMap();

	public List<Task> getByUser(Connection connection, int userSeq) {
		Task task = new Task();

		List<Object> paramList = Lists.newArrayList();
		paramList.add(userSeq);

		return (List<Task>) getByConditions(task, taskMap.getMap(), connection, GET_BY_SEQ, paramList);
	}

	private static final String GET_BY_SEQ = "SELECT * FROM TASK WHERE USER_SEQ = ?";

}
