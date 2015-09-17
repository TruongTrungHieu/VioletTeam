package com.hou.model;

import java.io.Serializable;

public class Anh_Lichtrinh implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String maAnh_lichtrinh;
	private String maLichtrinh;
	private String maUser;
	private String image;
	private String thoigian;
	
	public Anh_Lichtrinh() {
		
	}

	public Anh_Lichtrinh(String maAnh_lichtrinh, String maLichtrinh,
			String maUser, String image, String thoigian) {
		super();
		this.maAnh_lichtrinh = maAnh_lichtrinh;
		this.maLichtrinh = maLichtrinh;
		this.maUser = maUser;
		this.image = image;
		this.thoigian = thoigian;
	}

	public String getMaAnh_lichtrinh() {
		return maAnh_lichtrinh;
	}

	public void setMaAnh_lichtrinh(String maAnh_lichtrinh) {
		this.maAnh_lichtrinh = maAnh_lichtrinh;
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
