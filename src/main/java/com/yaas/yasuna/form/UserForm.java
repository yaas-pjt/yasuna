package com.yaas.yasuna.form;

import java.util.Date;

public class UserForm {
	private int seq;
	private String userId;
	private String userName;
	private String password;
	private int status;
	private Date prcDate;

	public UserForm() {
		super();
	}

	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getPrcDate() {
		return prcDate;
	}
	public void setPrcDate(Date prcDate) {
		this.prcDate = prcDate;
	}

}
