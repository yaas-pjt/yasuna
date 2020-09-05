package com.yaas.yasuna.form;

import java.time.LocalDate;
import java.util.Date;

public class TaskForm {

	private long seq;
	private long fkUserSeq;
	private long parentTaskId;
	private String title;
	private String memo;
	private String status;
	private String category;
	private LocalDate sDate;
	private LocalDate eDate;
	private LocalDate deadline;
	private Date prcDate;

	public TaskForm() {
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

	public long getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(long parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getsDate() {
		return sDate;
	}

	public void setsDate(LocalDate sDate) {
		this.sDate = sDate;
	}

	public LocalDate geteDate() {
		return eDate;
	}

	public void seteDate(LocalDate eDate) {
		this.eDate = eDate;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public Date getPrcDate() {
		return prcDate;
	}

	public void setPrcDate(Date prcDate) {
		this.prcDate = prcDate;
	}

}
