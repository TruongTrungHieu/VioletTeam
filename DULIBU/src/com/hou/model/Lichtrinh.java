package com.hou.model;

import java.io.Serializable;

public class Lichtrinh implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String maLichtrinh;
	private String tenLichtrinh;
	private String diemBatdau;
	private String diemKetthuc;
	private String tgBatdau;
	private String tgKetthuc;
	private String trangthai_hienthi;
	private String trangthai_hoatdong;
	private String trangthai_sos;
	private String admin;
	private String tg_checkserver;
	private String m_checkserver;
	private int chiphicanhan;
	private int chiphicadoan;
	private String thuquy;
	private String image;
	private String diadiem_xuatphat;
	private String thoigian_xuatphat;
	private String note;
	
	public Lichtrinh() {
		
	}

	

	public Lichtrinh(String maLichtrinh, String tenLichtrinh,
			String diemBatdau, String diemKetthuc, String tgBatdau,
			String tgKetthuc, String trangthai_hienthi,
			String trangthai_hoatdong, String trangthai_sos, String admin,
			String tg_checkserver, String m_checkserver, int chiphicanhan,
			int chiphicadoan, String thuquy, String image,
			String diadiem_xuatphat, String thoigian_xuatphat, String note) {
		super();
		this.maLichtrinh = maLichtrinh;
		this.tenLichtrinh = tenLichtrinh;
		this.diemBatdau = diemBatdau;
		this.diemKetthuc = diemKetthuc;
		this.tgBatdau = tgBatdau;
		this.tgKetthuc = tgKetthuc;
		this.trangthai_hienthi = trangthai_hienthi;
		this.trangthai_hoatdong = trangthai_hoatdong;
		this.trangthai_sos = trangthai_sos;
		this.admin = admin;
		this.tg_checkserver = tg_checkserver;
		this.m_checkserver = m_checkserver;
		this.chiphicanhan = chiphicanhan;
		this.chiphicadoan = chiphicadoan;
		this.thuquy = thuquy;
		this.image = image;
		this.diadiem_xuatphat = diadiem_xuatphat;
		this.thoigian_xuatphat = thoigian_xuatphat;
		this.note = note;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	public String getMaLichtrinh() {
		return maLichtrinh;
	}

	public void setMaLichtrinh(String maLichtrinh) {
		this.maLichtrinh = maLichtrinh;
	}

	public String getTenLichtrinh() {
		return tenLichtrinh;
	}

	public void setTenLichtrinh(String tenLichtrinh) {
		this.tenLichtrinh = tenLichtrinh;
	}

	public String getDiemBatdau() {
		return diemBatdau;
	}

	public void setDiemBatdau(String diemBatdau) {
		this.diemBatdau = diemBatdau;
	}

	public String getDiemKetthuc() {
		return diemKetthuc;
	}

	public void setDiemKetthuc(String diemKetthuc) {
		this.diemKetthuc = diemKetthuc;
	}

	public String getTgKetthuc() {
		return tgKetthuc;
	}

	public void setTgKetthuc(String tgKetthuc) {
		this.tgKetthuc = tgKetthuc;
	}

	public String getTrangthai_hienthi() {
		return trangthai_hienthi;
	}

	public void setTrangthai_hienthi(String trangthai_hienthi) {
		this.trangthai_hienthi = trangthai_hienthi;
	}

	public String getTrangthai_hoatdong() {
		return trangthai_hoatdong;
	}

	public void setTrangthai_hoatdong(String trangthai_hoatdong) {
		this.trangthai_hoatdong = trangthai_hoatdong;
	}

	public String getTrangthai_sos() {
		return trangthai_sos;
	}

	public void setTrangthai_sos(String trangthai_sos) {
		this.trangthai_sos = trangthai_sos;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getTg_checkserver() {
		return tg_checkserver;
	}

	public void setTg_checkserver(String tg_checkserver) {
		this.tg_checkserver = tg_checkserver;
	}

	public String getM_checkserver() {
		return m_checkserver;
	}

	public void setM_checkserver(String m_checkserver) {
		this.m_checkserver = m_checkserver;
	}

	public int getChiphicanhan() {
		return chiphicanhan;
	}

	public void setChiphicanhan(int chiphicanhan) {
		this.chiphicanhan = chiphicanhan;
	}

	public int getChiphicadoan() {
		return chiphicadoan;
	}

	public void setChiphicadoan(int chiphicadoan) {
		this.chiphicadoan = chiphicadoan;
	}

	public String getThuquy() {
		return thuquy;
	}

	public void setThuquy(String thuquy) {
		this.thuquy = thuquy;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.thuquy = image;
	}	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTgBatdau() {
		return tgBatdau;
	}

	public void setTgBatdau(String tgBatdau) {
		this.tgBatdau = tgBatdau;
	}

	public String getDiadiem_xuatphat() {
		return diadiem_xuatphat;
	}

	public void setDiadiem_xuatphat(String diadiem_xuatphat) {
		this.diadiem_xuatphat = diadiem_xuatphat;
	}

	public String getThoigian_xuatphat() {
		return thoigian_xuatphat;
	}

	public void setThoigian_xuatphat(String thoigian_xuatphat) {
		this.thoigian_xuatphat = thoigian_xuatphat;
	}
	

}
