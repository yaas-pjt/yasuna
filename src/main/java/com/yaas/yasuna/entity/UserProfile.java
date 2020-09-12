package com.yaas.yasuna.entity;

public class UserProfile {

	private long seq;
	private long fkUserSeq;
	private String familyName;
	private String firstName;
	private byte[] picture;
	private String mail;
	private String organization;


	public UserProfile() {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
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
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}



}
