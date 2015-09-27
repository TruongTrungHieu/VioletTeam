package com.hou.model;

import java.io.Serializable;

public class Chitieu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String maChitieu;
	private String maLichtrinh;
	private String tenChitieu;
	private String thoigian;
	private double sotien;
	private String filedinhkem;
	private String maUser;
	
	public Chitieu() {
		
	}

	public Chitieu(String maChitieu, String maLichtrinh, String tenChitieu,
			String thoigian, double sotien, String filedinhkem, String maUser) {
		super();
		this.maChitieu = maChitieu;
		this.maLichtrinh = maLichtrinh;
		this.tenChitieu = tenChitieu;
		this.thoigian = thoigian;
		this.sotien = sotien;
		this.filedinhkem = filedinhkem;
		this.maUser = maUser;
	}

	public String getMaChitieu() {
		return maChitieu;
	}

	public void setMaChitieu(String maChitieu) {
		this.maChitieu = maChitieu;
	}

	public String getMaLichtrinh() {
		return maLichtrinh;
	}

	public void setMaLichtrinh(String maLichtrinh) {
		this.maLichtrinh = maLichtrinh;
	}

	public String getTenChitieu() {
		return tenChitieu;
	}

	public void setTenChitieu(String tenChitieu) {
		this.tenChitieu = tenChitieu;
	}

	public String getThoigian() {
		return thoigian;
	}

	public void setThoigian(String thoigian) {
		this.thoigian = thoigian;
	}

	public double getSotien() {
		return sotien;
	}

	public void setSotien(double sotien) {
		this.sotien = sotien;
	}

	public String getFiledinhkem() {
		return filedinhkem;
	}

	public void setFiledinhkem(String filedinhkem) {
		this.filedinhkem = filedinhkem;
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
