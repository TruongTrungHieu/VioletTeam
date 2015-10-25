package com.hou.model;

public class LichtrinhMember {
	private String maLichtrinh;
	private String maUser;
	private String trangthai_antoan;
	private String trangthai_ketnoi;
	private String quyen;
	private String trangthai_thamgia;
	private String tenUser;
	private String image;
	
	
	public LichtrinhMember() {
		// TODO Auto-generated constructor stub
	}

	public LichtrinhMember(String maLichtrinh, String maUser,
			String trangthai_antoan, String trangthai_ketnoi, String quyen,
			String trangthai_thamgia, String tenUser, String image) {
		super();
		this.maLichtrinh = maLichtrinh;
		this.maUser = maUser;
		this.trangthai_antoan = trangthai_antoan;
		this.trangthai_ketnoi = trangthai_ketnoi;
		this.quyen = quyen;
		this.trangthai_thamgia = trangthai_thamgia;
		this.tenUser = tenUser;
		this.image = image;
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
	public String getTrangthai_antoan() {
		return trangthai_antoan;
	}
	public void setTrangthai_antoan(String trangthai_antoan) {
		this.trangthai_antoan = trangthai_antoan;
	}
	public String getTrangthai_ketnoi() {
		return trangthai_ketnoi;
	}
	public void setTrangthai_ketnoi(String trangthai_ketnoi) {
		this.trangthai_ketnoi = trangthai_ketnoi;
	}
	public String getQuyen() {
		return quyen;
	}
	public void setQuyen(String quyen) {
		this.quyen = quyen;
	}
	public String getTrangthai_thamgia() {
		return trangthai_thamgia;
	}
	public void setTrangthai_thamgia(String trangthai_thamgia) {
		this.trangthai_thamgia = trangthai_thamgia;
	}
	public String getTenUser() {
		return tenUser;
	}
	public void setTenUser(String tenUser) {
		this.tenUser = tenUser;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
}
