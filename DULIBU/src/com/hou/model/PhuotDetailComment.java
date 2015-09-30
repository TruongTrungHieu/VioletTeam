package com.hou.model;

public class PhuotDetailComment {
	String maComment, comment, time;
	public PhuotDetailComment(String maComment, String comment, String time){
		this.maComment = maComment;
		this.comment = comment;
		this.time = time;
	}
	public String getMaComment() {
		return maComment;
	}
	public void setMaComment(String maComment) {
		this.maComment = maComment;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
