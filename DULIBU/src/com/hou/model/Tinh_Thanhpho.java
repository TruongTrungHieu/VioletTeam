package com.hou.model;

import java.io.Serializable;

public class Tinh_Thanhpho implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String maTinh;
	private String tenTinh;
	private String lat;
	private String lon;
	private String image;
	private String ghichu;
	
	public Tinh_Thanhpho() {
		
	}

	public Tinh_Thanhpho(String maTinh, String tenTinh, String lat, String lon,
			String image, String ghichu) {
		super();
		this.maTinh = maTinh;
		this.tenTinh = tenTinh;
		this.lat = lat;
		this.lon = lon;
		this.image = image;
		this.ghichu = ghichu;
	}

	public String getMaTinh() {
		return maTinh;
	}

	public void setMaTinh(String maTinh) {
		this.maTinh = maTinh;
	}

	public String getTenTinh() {
		return tenTinh;
	}

	public void setTenTinh(String tenTinh) {
		this.tenTinh = tenTinh;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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
