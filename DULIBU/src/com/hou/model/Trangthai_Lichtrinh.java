package com.hou.model;

import java.io.Serializable;

public class Trangthai_Lichtrinh implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String maTrangthai_lichtrinh;
	private String tenTrangthai_lichtrinh;
	private String ghichu;

	public Trangthai_Lichtrinh() {

	}

	public Trangthai_Lichtrinh(String maTrangthai_lichtrinh,
			String tenTrangthai_lichtrinh, String ghichu) {
		super();
		this.maTrangthai_lichtrinh = maTrangthai_lichtrinh;
		this.tenTrangthai_lichtrinh = tenTrangthai_lichtrinh;
		this.ghichu = ghichu;
	}

	public String getMaTrangthai_lichtrinh() {
		return maTrangthai_lichtrinh;
	}

	public void setMaTrangthai_lichtrinh(String maTrangthai_lichtrinh) {
		this.maTrangthai_lichtrinh = maTrangthai_lichtrinh;
	}

	public String getTenTrangthai_lichtrinh() {
		return tenTrangthai_lichtrinh;
	}

	public void setTenTrangthai_lichtrinh(String tenTrangthai_lichtrinh) {
		this.tenTrangthai_lichtrinh = tenTrangthai_lichtrinh;
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
