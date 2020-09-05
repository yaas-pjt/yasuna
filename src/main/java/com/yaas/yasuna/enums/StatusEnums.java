package com.yaas.yasuna.enums;

public enum StatusEnums {

	YET("未着手",0),
	DOING("着手中", 1),
	ALMOST("もうすぐ終わる", 2),
	PROBLEM("課題あり", 3),
	EMERGENCY("要救助", 4),
	DONE("完了", 9);

	private String label;
	private int id;

	private StatusEnums(String label, int id) {
		this.label = label;
		this.id= id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
