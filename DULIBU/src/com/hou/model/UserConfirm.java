package com.hou.model;

public class UserConfirm {
	private String maUser, tenUser;
	int sttConfirm;
	public UserConfirm(){
		
	}
	public UserConfirm(String maUser, String tenUser, int sttConfirm){
		super();
		this.maUser = maUser;
		this.tenUser = tenUser;
		this.sttConfirm = sttConfirm;
	}
	public String getMaUser() {
		return maUser;
	}
	public void setMaUser(String maUser) {
		this.maUser = maUser;
	}
	public String getTenUser() {
		return tenUser;
	}
	public void setTenUser(String tenUser) {
		this.tenUser = tenUser;
	}
	public int getSttConfirm() {
		return sttConfirm;
	}
	public void setSttConfirm(int sttConfirm) {
		this.sttConfirm = sttConfirm;
	}
	
}
