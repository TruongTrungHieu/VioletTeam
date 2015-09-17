package com.hou.model;

import java.io.Serializable;

public class Chatluong_Lichtrinh implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String maChatluong;
	private String tenChatluong;
	private String ghichu;

	public Chatluong_Lichtrinh() {

	}

	public Chatluong_Lichtrinh(String maChatluong, String tenChatluong,
			String ghichu) {
		super();
		this.maChatluong = maChatluong;
		this.tenChatluong = tenChatluong;
		this.ghichu = ghichu;
	}

	public String getMaChatluong() {
		return maChatluong;
	}

	public void setMaChatluong(String maChatluong) {
		this.maChatluong = maChatluong;
	}

	public String getTenChatluong() {
		return tenChatluong;
	}

	public void setTenChatluong(String tenChatluong) {
		this.tenChatluong = tenChatluong;
	}

	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
