package com.hou.model;

import java.io.Serializable;

public class Lichtrinh_User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String maLichtrinh;
	private String maUser;
	private String trangthai_antoan;
	private String trangthai_ketnoi;
	private String quyen;
	private int trangthai_thamgia;
	
	public Lichtrinh_User() {
		
	}

	public Lichtrinh_User(String maLichtrinh, String maUser,
			String trangthai_antoan, String trangthai_ketnoi, String quyen,
			int trangthai_thamgia) {
		super();
		this.maLichtrinh = maLichtrinh;
		this.maUser = maUser;
		this.trangthai_antoan = trangthai_antoan;
		this.trangthai_ketnoi = trangthai_ketnoi;
		this.quyen = quyen;
		this.trangthai_thamgia = trangthai_thamgia;
	}

	public String getMaLichtrinh() {
		return maLichtrinh;
	}

	public void setMaLichtrinh(String maLichtrinh) {
		this.maLichtrinh = maLichtrinh;
	}

	public String getMaUser() {
		return maUser;
	}

	public void setMaUser(String maUser) {
		this.maUser = maUser;
	}

	public String getTrangthai_antoan() {
		return trangthai_antoan;
	}

	public void setTrangthai_antoan(String trangthai_antoan) {
		this.trangthai_antoan = trangthai_antoan;
	}

	public String getTrangthai_ketnoi() {
		return trangthai_ketnoi;
	}

	public void setTrangthai_ketnoi(String trangthai_ketnoi) {
		this.trangthai_ketnoi = trangthai_ketnoi;
	}

	public String getQuyen() {
		return quyen;
	}

	public void setQuyen(String quyen) {
		this.quyen = quyen;
	}

	public int getTrangthai_thamgia() {
		return trangthai_thamgia;
	}

	public void setTrangthai_thamgia(int trangthai_thamgia) {
		this.trangthai_thamgia = trangthai_thamgia;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

