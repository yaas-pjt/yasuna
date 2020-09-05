package com.yaas.yasuna.form;

import java.util.Date;

public class QuickSettingForm {
	private long seq;
	private long fkUserSeq;
	private int categorySetting;
	private int statusSetting;
	private long sDateSetting;
	private long deadlineSetting;
	private Date prcDate;

	public QuickSettingForm() {
		super();
	}

	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public long getFkUserSeq() {
		return fkUserSeq;
	}
	public void setFkUserSeq(long fkUserSeq) {
		this.fkUserSeq = fkUserSeq;
	}
	public int getCategorySetting() {
		return categorySetting;
	}
	public void setCategorySetting(int categorySetting) {
		this.categorySetting = categorySetting;
	}
	public int getStatusSetting() {
		return statusSetting;
	}
	public void setStatusSetting(int statusSetting) {
		this.statusSetting = statusSetting;
	}
	public long getsDateSetting() {
		return sDateSetting;
	}
	public void setsDateSetting(long sDateSetting) {
		this.sDateSetting = sDateSetting;
	}
	public long getDeadlineSetting() {
		return deadlineSetting;
	}
	public void setDeadlineSetting(long deadlineSetting) {
		this.deadlineSetting = deadlineSetting;
	}
	public Date getPrcDate() {
		return prcDate;
	}
	public void setPrcDate(Date prcDate) {
		this.prcDate = prcDate;
	}

}
