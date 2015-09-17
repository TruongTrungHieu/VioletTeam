package com.hou.model;

import java.io.Serializable;

public class Lichtrinh_Diemphuot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String maLichtrinh_diemphuot;
	private String maLichtrinh;
	private String maDiemphuot;
	private String tenDiemphuot;
	private String lat;
	private String lon;
	private String maTinh;
	private String ghichu;
	private String image;
	private String diachi;
	private int sothutu;
	
	public Lichtrinh_Diemphuot() {
		
	}

	public Lichtrinh_Diemphuot(String maLichtrinh_diemphuot,
			String maLichtrinh, String maDiemphuot, String tenDiemphuot,
			String lat, String lon, String maTinh, String ghichu, String image,
			String diachi, int sothutu) {
		super();
		this.maLichtrinh_diemphuot = maLichtrinh_diemphuot;
		this.maLichtrinh = maLichtrinh;
		this.maDiemphuot = maDiemphuot;
		this.tenDiemphuot = tenDiemphuot;
		this.lat = lat;
		this.lon = lon;
		this.maTinh = maTinh;
		this.ghichu = ghichu;
		this.image = image;
		this.diachi = diachi;
		this.sothutu = sothutu;
	}

	public String getMaLichtrinh_diemphuot() {
		return maLichtrinh_diemphuot;
	}

	public void setMaLichtrinh_diemphuot(String maLichtrinh_diemphuot) {
		this.maLichtrinh_diemphuot = maLichtrinh_diemphuot;
	}

	public String getMaLichtrinh() {
		return maLichtrinh;
	}

	public void setMaLichtrinh(String maLichtrinh) {
		this.maLichtrinh = maLichtrinh;
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

	public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public int getSothutu() {
		return sothutu;
	}

	public void setSothutu(int sothutu) {
		this.sothutu = sothutu;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
