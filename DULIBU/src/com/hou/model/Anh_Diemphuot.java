package com.hou.model;

import java.io.Serializable;

public class Anh_Diemphuot implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String maAnh_diemphuot;
	private String maDiemphuot;
	private String maUser;
	private String image;
	private String thoigian;
	
	public Anh_Diemphuot() {
		
	}

	public Anh_Diemphuot(String maAnh_diemphuot, String maDiemphuot,
			String maUser, String image, String thoigian) {
		super();
		this.maAnh_diemphuot = maAnh_diemphuot;
		this.maDiemphuot = maDiemphuot;
		this.maUser = maUser;
		this.image = image;
		this.thoigian = thoigian;
	}

	public String getMaAnh_diemphuot() {
		return maAnh_diemphuot;
	}

	public void setMaAnh_diemphuot(String maAnh_diemphuot) {
		this.maAnh_diemphuot = maAnh_diemphuot;
	}

	public String getMaDiemphuot() {
		return maDiemphuot;
	}

	public void setMaDiemphuot(String maDiemphuot) {
		this.maDiemphuot = maDiemphuot;
	}

	public String getMaUser() {
		return maUser;
	}

	public void setMaUser(String maUser) {
		this.maUser = maUser;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getThoigian() {
		return thoigian;
	}

	public void setThoigian(String thoigian) {
		this.thoigian = thoigian;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		
}
