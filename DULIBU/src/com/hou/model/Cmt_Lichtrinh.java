package com.hou.model;

import java.io.Serializable;

public class Cmt_Lichtrinh implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String maComment;
	private String maLichtrinh;
	private String noidung;
	private String thoigian;
	private String maChatluong_lichtrinh;
	private String maUser;
	
	public Cmt_Lichtrinh() {
		
	}

	public Cmt_Lichtrinh(String maComment, String maLichtrinh, String noidung,
			String thoigian, String maChatluong_lichtrinh, String maUser) {
		super();
		this.maComment = maComment;
		this.maLichtrinh = maLichtrinh;
		this.noidung = noidung;
		this.thoigian = thoigian;
		this.maChatluong_lichtrinh = maChatluong_lichtrinh;
		this.maUser = maUser;
	}

	public String getMaComment() {
		return maComment;
	}

	public void setMaComment(String maComment) {
		this.maComment = maComment;
	}

	public String getMaLichtrinh() {
		return maLichtrinh;
	}

	public void setMaLichtrinh(String maLichtrinh) {
		this.maLichtrinh = maLichtrinh;
	}

	public String getNoidung() {
		return noidung;
	}

	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}

	public String getThoigian() {
		return thoigian;
	}

	public void setThoigian(String thoigian) {
		this.thoigian = thoigian;
	}

	public String getMaChatluong_lichtrinh() {
		return maChatluong_lichtrinh;
	}

	public void setMaChatluong_lichtrinh(String maChatluong_lichtrinh) {
		this.maChatluong_lichtrinh = maChatluong_lichtrinh;
	}

	public String getMaUser() {
		return maUser;
	}

	public void setMaUser(String maUser) {
		this.maUser = maUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
