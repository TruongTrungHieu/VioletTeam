package com.hou.model;

public class Nearby {
	private String ten;
	private String diachi;
	private double lat;
	private double lon;
	private String icon;
	
	public Nearby() {
		
	}

	public Nearby(String ten, String diachi, double lat, double lon, String icon) {
		super();
		this.ten = ten;
		this.diachi = diachi;
		this.lat = lat;
		this.lon = lon;
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
	
}
