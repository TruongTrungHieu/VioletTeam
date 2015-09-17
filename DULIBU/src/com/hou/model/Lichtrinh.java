package com.hou.model;

import java.io.Serializable;

public class Lichtrinh implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String maLichtrinh;
	private String tenLichtrinh;
	private String diemBatdau;
	private String diemKetthuc;
	private String tbBatdau;
	private String tgKetthuc;
	private String trangthai_hienthi;
	private String trangthai_hoatdong;
	private String trangthai_sos;
	private String admin;
	private String chatluong_lichtrinh;
	private String tg_checkserver;
	private String m_checkserver;
	
	public Lichtrinh() {
		
	}

	public Lichtrinh(String maLichtrinh, String tenLichtrinh,
			String diemBatdau, String diemKetthuc, String tbBatdau,
			String tgKetthuc, String trangthai_hienthi,
			String trangthai_hoatdong, String trangthai_sos, String admin,
			String chatluong_lichtrinh, String tg_checkserver,
			String m_checkserver) {
		super();
		this.maLichtrinh = maLichtrinh;
		this.tenLichtrinh = tenLichtrinh;
		this.diemBatdau = diemBatdau;
		this.diemKetthuc = diemKetthuc;
		this.tbBatdau = tbBatdau;
		this.tgKetthuc = tgKetthuc;
		this.trangthai_hienthi = trangthai_hienthi;
		this.trangthai_hoatdong = trangthai_hoatdong;
		this.trangthai_sos = trangthai_sos;
		this.admin = admin;
		this.chatluong_lichtrinh = chatluong_lichtrinh;
		this.tg_checkserver = tg_checkserver;
		this.m_checkserver = m_checkserver;
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

	public String getTbBatdau() {
		return tbBatdau;
	}

	public void setTbBatdau(String tbBatdau) {
		this.tbBatdau = tbBatdau;
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

	public String getChatluong_lichtrinh() {
		return chatluong_lichtrinh;
	}

	public void setChatluong_lichtrinh(String chatluong_lichtrinh) {
		this.chatluong_lichtrinh = chatluong_lichtrinh;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
