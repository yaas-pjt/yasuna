package com.yaas.yasuna.enums;

public class GridHeaderEnums {

	public enum TaskGridHeaderEnum {
		INBOX("インボックス"),
		QUICK("すぐやる"),
		ASK("おねがい"),
		NEXT("次にやる");

		private String label;

		TaskGridHeaderEnum(String label){
			this.label = label;
		}

		public String getLabel() {
			return this.label;
		}
	}
}