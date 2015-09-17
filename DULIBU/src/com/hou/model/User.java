package com.hou.model;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String maUser;
	private String fullname;
	private String email;
	private String ngaysinh;
	private String sdt;
	private int gioitinh;
	private String sdt_lienhe;
	private String avatar;
	private String ghichu;
	
	public User() {
		
	}

	public User(String fullname, String email, String ngaysinh,
			String sdt, int gioitinh, String sdt_lienhe, String avatar,
			String ghichu) {
		super();
		this.fullname = fullname;
		this.email = email;
		this.ngaysinh = ngaysinh;
		this.sdt = sdt;
		this.gioitinh = gioitinh;
		this.sdt_lienhe = sdt_lienhe;
		this.avatar = avatar;
		this.ghichu = ghichu;
	}
	
	public User(String maUser, String fullname, String email, String ngaysinh,
			String sdt, int gioitinh, String sdt_lienhe, String avatar,
			String ghichu) {
		super();
		this.maUser = maUser;
		this.fullname = fullname;
		this.email = email;
		this.ngaysinh = ngaysinh;
		this.sdt = sdt;
		this.gioitinh = gioitinh;
		this.sdt_lienhe = sdt_lienhe;
		this.avatar = avatar;
		this.ghichu = ghichu;
	}

	public String getMaUser() {
		return maUser;
	}

	public void setMaUser(String maUser) {
		this.maUser = maUser;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNgaysinh() {
		return ngaysinh;
	}

	public void setNgaysinh(String ngaysinh) {
		this.ngaysinh = ngaysinh;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public int getGioitinh() {
		return gioitinh;
	}

	public void setGioitinh(int gioitinh) {
		this.gioitinh = gioitinh;
	}

	public String getSdt_lienhe() {
		return sdt_lienhe;
	}

	public void setSdt_lienhe(String sdt_lienhe) {
		this.sdt_lienhe = sdt_lienhe;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
