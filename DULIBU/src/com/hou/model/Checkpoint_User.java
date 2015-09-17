package com.hou.model;

import java.io.Serializable;

public class Checkpoint_User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String maLichtrinh;
	private String maUser;
	private String lat;
	private String lon;
	private String thoigian;
	private String ghichu;
	
	public Checkpoint_User() {
		
	}

	public Checkpoint_User(String maLichtrinh, String maUser, String lat,
			String lon, String thoigian, String ghichu) {
		super();
		this.maLichtrinh = maLichtrinh;
		this.maUser = maUser;
		this.lat = lat;
		this.lon = lon;
		this.thoigian = thoigian;
		this.ghichu = ghichu;
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

	public String getThoigian() {
		return thoigian;
	}

	public void setThoigian(String thoigian) {
		this.thoigian = thoigian;
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
