package com.hou.model;

import java.io.Serializable;

public class Diemphuot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private String maDiemphuot;
	private String tenDiemphuot;
	private String lat;
	private String lon;
	private String maTinh;
	private String diachi;
	private String ghichu;
	private String image;
	private int trangthaiChuan;

	public Diemphuot() {
		
	}

	public Diemphuot(String maDiemphuot, String tenDiemphuot, String lat,
			String lon, String maTinh, String diachi, String ghichu,
			String image, int trangthaiChuan) {
		super();
		this.maDiemphuot = maDiemphuot;
		this.tenDiemphuot = tenDiemphuot;
		this.lat = lat;
		this.lon = lon;
		this.maTinh = maTinh;
		this.diachi = diachi;
		this.ghichu = ghichu;
		this.image = image;
		this.trangthaiChuan = trangthaiChuan;
	}

	public String getMaDiemphuot() {
		return maDiemphuot;
	}

	public void setMaDiemphuot(String maDiemphuot) {
		this.maDiemphuot = maDiemphuot;
	}

	public String getTenDiemphuot() {
		return tenDiemphuot;
	}

	public void setTenDiemphuot(String tenDiemphuot) {
		this.tenDiemphuot = tenDiemphuot;
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

	public String getMaTinh() {
		return maTinh;
	}

	public void setMaTinh(String maTinh) {
		this.maTinh = maTinh;
	}

	public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getTrangthaiChuan() {
		return trangthaiChuan;
	}

	public void setTrangthaiChuan(int trangthaiChuan) {
		this.trangthaiChuan = trangthaiChuan;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
