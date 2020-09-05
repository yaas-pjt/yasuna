package com.yaas.yasuna.enums;

public class GridIdEnums {
	public enum TaskGridIdEnum {
		INBOX("inbox"),
		QUICK("quick"),
		ASK("ask"),
		NEXT("next");

		private String label;

		TaskGridIdEnum(String label){
			this.label = label;
		}

		public String getLabel() {
			return this.label;
		}
	}

}
