package com.hou.model;
import com.google.android.gms.maps.model.Marker;

public class InfoTracking {
	private String target_id;
	private String target_type;
	private String user_id;
	private String user_fullname;
	private String avatar;
	private double lat;
	private double lon;
	
	private Marker marker = null;
	
	public InfoTracking() {
		// TODO Auto-generated constructor stub
	}

	public InfoTracking(String target_id, String target_type, String user_id,
			String user_fullname, String avatar, double lat, double lon) {
		super();
		this.target_id = target_id;
		this.target_type = target_type;
		this.user_id = user_id;
		this.user_fullname = user_fullname;
		this.avatar = avatar;
		this.lat = lat;
		this.lon = lon;
	}

	public String getTarget_id() {
		return target_id;
	}

	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}

	public String getTarget_type() {
		return target_type;
	}

	public void setTarget_type(String target_type) {
		this.target_type = target_type;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_fullname() {
		return user_fullname;
	}

	public void setUser_fullname(String user_fullname) {
		this.user_fullname = user_fullname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
	
	public Marker getMarker() {
		return this.marker;
	}

	public void setMarker(Marker marker) {
		// TODO Auto-generated method stub
		this.marker = marker;
		this.setLat(marker.getPosition().latitude);
		this.setLon(marker.getPosition().longitude);
	}
	
	
	
}
