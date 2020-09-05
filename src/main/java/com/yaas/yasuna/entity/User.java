package com.yaas.yasuna.entity;

import java.util.Date;

public class User {

	private long seq;
	private String userId;
	private String displayName;
	private String password;
	private int status;
	private long roleId;
	private Date prcDate;

	public User() {
		super();
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public Date getPrcDate() {
		return prcDate;
	}

	public void setPrcDate(Date prcDate) {
		this.prcDate = prcDate;
	}
}
