package com.hou.adapters;

public class MyPlace {
	private String title;
	private String diachi;
	private String dokho;
	private String imgPath;
	public MyPlace() {
		// TODO Auto-generated constructor stub
	}
	
	public MyPlace(String title, String diachi, String dokho, String imgPath) {
		super();
		this.title = title;
		this.diachi = diachi;
		this.dokho = dokho;
		this.imgPath = imgPath;
	}



	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDiachi() {
		return diachi;
	}
	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}
	public String getDokho() {
		return dokho;
	}
	public void setDokho(String dokho) {
		this.dokho = dokho;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	
	
}
