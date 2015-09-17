package com.hou.model;

import java.io.Serializable;

public class Cmt_Diemphuot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String maComment;
	private String maDiemphuot;
	private String noidung;
	private String thoigian;
	private String maDokho_diemphuot;
	private String maUser;
	
	public Cmt_Diemphuot() {
		
	}

	public Cmt_Diemphuot(String maComment, String maDiemphuot, String noidung,
			String thoigian, String maDokho_diemphuot, String maUser) {
		super();
		this.maComment = maComment;
		this.maDiemphuot = maDiemphuot;
		this.noidung = noidung;
		this.thoigian = thoigian;
		this.maDokho_diemphuot = maDokho_diemphuot;
		this.maUser = maUser;
	}

	public String getMaComment() {
		return maComment;
	}

	public void setMaComment(String maComment) {
		this.maComment = maComment;
	}

	public String getMaDiemphuot() {
		return maDiemphuot;
	}

	public void setMaDiemphuot(String maDiemphuot) {
		this.maDiemphuot = maDiemphuot;
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

	public String getMaDokho_diemphuot() {
		return maDokho_diemphuot;
	}

	public void setMaDokho_diemphuot(String maDokho_diemphuot) {
		this.maDokho_diemphuot = maDokho_diemphuot;
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
