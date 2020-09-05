package com.yaas.yasuna.enums;

public class GridColumnEnums {

	public enum TaskGridColumnEnum {
		TITLE("タイトル"),
		MEMO("メモ"),
		STATUS("ステータス"),
		CATEGORY("分類"),
		SDATE("開始日"),
		EDATE("終了日"),
		DEADLINE("対応期限");

		private String label;

		TaskGridColumnEnum(String label){
			this.label = label;
		}

		public String getLabel() {
			return this.label;
		}
	}
}
