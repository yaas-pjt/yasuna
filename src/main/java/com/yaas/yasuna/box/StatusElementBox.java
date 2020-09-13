package com.yaas.yasuna.box;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.yaas.yasuna.enums.StatusEnums;

public class StatusElementBox {

	private List<String> statusElementList;

	public List<String> getStatusElementList() {

		generateElementList();

		return statusElementList;
	}

	private void generateElementList() {
		statusElementList = Lists.newArrayList();
		statusElementList.add(StatusEnums.YET.getLabel());
		statusElementList.add(StatusEnums.DOING.getLabel());
		statusElementList.add(StatusEnums.ALMOST.getLabel());
		statusElementList.add(StatusEnums.PROBLEM.getLabel());
		statusElementList.add(StatusEnums.EMERGENCY.getLabel());
		statusElementList.add(StatusEnums.DONE.getLabel());

	}
}