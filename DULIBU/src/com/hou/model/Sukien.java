package com.hou.model;

import java.io.Serializable;

public class Sukien implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String maSukien = "";
	private String maLichtrinh = "";
	private String tenSukien = "";
	private String thoigian = "";
	private String diadiem = "";
	private String lat = "";
	private String lon = "";
	
	public Sukien() {
		
	}

	public Sukien(String maSukien, String maLichtrinh, String tenSukien,
			String thoigian, String diadiem, String lat, String lon) {
		super();
		this.maSukien = maSukien;
		this.maLichtrinh = maLichtrinh;
		this.tenSukien = tenSukien;
		this.thoigian = thoigian;
		this.diadiem = diadiem;
		this.lat = lat;
		this.lon = lon;
	}

	public String getMaSukien() {
		return maSukien;
	}

	public void setMaSukien(String maSukien) {
		this.maSukien = maSukien;
	}

	public String getMaLichtrinh() {
		return maLichtrinh;
	}

	public void setMaLichtrinh(String maLichtrinh) {
		this.maLichtrinh = maLichtrinh;
	}

	public String getTenSukien() {
		return tenSukien;
	}

	public void setTenSukien(String tenSukien) {
		this.tenSukien = tenSukien;
	}

	public String getThoigian() {
		return thoigian;
	}

	public void setThoigian(String thoigian) {
		this.thoigian = thoigian;
	}

	public String getDiadiem() {
		return diadiem;
	}

	public void setDiadiem(String diadiem) {
		this.diadiem = diadiem;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
