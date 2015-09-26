package com.hou.app;

import java.io.File;
import java.util.ArrayList;

import com.hou.model.Diemphuot;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

public class Global {
	public static ArrayList<Diemphuot> LIST_DIEMPHUOT = new ArrayList<Diemphuot>();
	public static String getURI(String image){
		File mediaStorageDir;
		if (Build.VERSION.SDK_INT > 8) {
			mediaStorageDir = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		} else {
			mediaStorageDir = new File(
					Environment.getExternalStorageDirectory(), "Pictures");
		}
		
		return "file://"+ mediaStorageDir.getPath() + "/" + image;
	}
}
