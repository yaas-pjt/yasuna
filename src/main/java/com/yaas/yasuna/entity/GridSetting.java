package com.yaas.yasuna.entity;

import java.util.Date;

public class GridSetting {

	private long seq;
	private long fkUserSeq;
	private int titleFlg;
	private int statusFlg;
	private int memoFlg;
	private int categoryFlg;
	private int sDateFlg;
	private int eDateFlg;
	private int deadlineFlg;
	private Date prcDate;

	public GridSetting() {
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


	public int getTitleFlg() {
		return titleFlg;
	}


	public void setTitleFlg(int titleFlg) {
		this.titleFlg = titleFlg;
	}


	public int getStatusFlg() {
		return statusFlg;
	}


	public void setStatusFlg(int statusFlg) {
		this.statusFlg = statusFlg;
	}


	public int getMemoFlg() {
		return memoFlg;
	}


	public void setMemoFlg(int memoFlg) {
		this.memoFlg = memoFlg;
	}


	public int getCategoryFlg() {
		return categoryFlg;
	}


	public void setCategoryFlg(int categoryFlg) {
		this.categoryFlg = categoryFlg;
	}


	public int getsDateFlg() {
		return sDateFlg;
	}


	public void setsDateFlg(int sDateFlg) {
		this.sDateFlg = sDateFlg;
	}


	public int geteDateFlg() {
		return eDateFlg;
	}


	public void seteDateFlg(int eDateFlg) {
		this.eDateFlg = eDateFlg;
	}


	public int getDeadlineFlg() {
		return deadlineFlg;
	}


	public void setDeadlineFlg(int deadlineFlg) {
		this.deadlineFlg = deadlineFlg;
	}


	public Date getPrcDate() {
		return prcDate;
	}


	public void setPrcDate(Date prcDate) {
		this.prcDate = prcDate;
	}

}
