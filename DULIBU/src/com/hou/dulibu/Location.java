package com.hou.dulibu;

public class Location {
	 	public int id;
	    public String address;
	    public Double lng;
	    public Double lat;

	    public Location(int id,String address, Double lng, Double lat) {
	        this.address = address;
	        this.lng = lng;
	        this.lat = lat;
	        this.id = id;
	    }
	}

