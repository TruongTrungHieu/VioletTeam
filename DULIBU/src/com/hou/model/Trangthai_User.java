package com.hou.model;

import java.io.Serializable;

public class Trangthai_User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String maTrangthai;
	private String tenTrangthai;
	private String ghichu;
	
	public Trangthai_User() {
		
	}

	public Trangthai_User(String maTrangthai, String tenTrangthai, String ghichu) {
		super();
		this.maTrangthai = maTrangthai;
		this.tenTrangthai = tenTrangthai;
		this.ghichu = ghichu;
	}

	public String getMaTrangthai() {
		return maTrangthai;
	}

	public void setMaTrangthai(String maTrangthai) {
		this.maTrangthai = maTrangthai;
	}

	public String getTenTrangthai() {
		return tenTrangthai;
	}

	public void setTenTrangthai(String tenTrangthai) {
		this.tenTrangthai = tenTrangthai;
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
