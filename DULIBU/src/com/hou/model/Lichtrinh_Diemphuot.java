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
	private String lat;
	private String lon;
	private int sothutu;
	
	public Lichtrinh_Diemphuot() {
		
	}

	public Lichtrinh_Diemphuot(String maLichtrinh_diemphuot,
			String maLichtrinh, String maDiemphuot, String lat, String lon,
			int sothutu) {
		super();
		this.maLichtrinh_diemphuot = maLichtrinh_diemphuot;
		this.maLichtrinh = maLichtrinh;
		this.maDiemphuot = maDiemphuot;
		this.lat = lat;
		this.lon = lon;
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
