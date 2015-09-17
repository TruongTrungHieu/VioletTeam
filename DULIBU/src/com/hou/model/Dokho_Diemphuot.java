package com.hou.model;

import java.io.Serializable;

public class Dokho_Diemphuot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String maDokho;
	private String tenDokho;
	private String ghichu;

	public Dokho_Diemphuot() {

	}

	public Dokho_Diemphuot(String maDokho, String tenDokho, String ghichu) {
		super();
		this.maDokho = maDokho;
		this.tenDokho = tenDokho;
		this.ghichu = ghichu;
	}

	public String getMaDokho() {
		return maDokho;
	}

	public void setMaDokho(String maDokho) {
		this.maDokho = maDokho;
	}

	public String getTenDokho() {
		return tenDokho;
	}

	public void setTenDokho(String tenDokho) {
		this.tenDokho = tenDokho;
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
