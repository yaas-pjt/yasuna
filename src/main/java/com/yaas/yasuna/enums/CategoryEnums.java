package com.yaas.yasuna.enums;

public enum CategoryEnums {

	INBOX("インボックス",1),
	QUICK("すぐやる", 2),
	ASK("おねがい", 3),
	NEXT("いつかやる", 4);

	private String label;
	private int id;

	private CategoryEnums(String label, int id) {
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
