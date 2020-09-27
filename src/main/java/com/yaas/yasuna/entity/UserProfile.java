package com.yaas.yasuna.entity;

public class UserProfile {

	private long seq;
	private long fkUserSeq;
	private String firstName;
	private String lastName;
	private byte[] picture;
	private String org;

	public UserProfile() {
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

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}

}
