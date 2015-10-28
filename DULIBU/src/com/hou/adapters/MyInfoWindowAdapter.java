package com.hou.adapters;

import java.io.File;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.hou.app.Global;
import com.hou.dulibu.R;
import com.hou.ultis.ImageUltiFunctions;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyInfoWindowAdapter implements InfoWindowAdapter {

	private final View myContentsView;
	private LayoutInflater li;
	private int LayoutId;

	public MyInfoWindowAdapter(LayoutInflater li, int LayoutId) {
		myContentsView = li.inflate(LayoutId, null);
	}

	@Override
	public View getInfoContents(Marker marker) {

		TextView tvTitle = ((TextView) myContentsView.findViewById(R.id.customIWtitle));
		tvTitle.setText(marker.getTitle());
		ImageView iv = ((ImageView) myContentsView.findViewById(R.id.customIWimage));
		String imageLink = marker.getSnippet();
		String imageName;
		if (imageLink.length() > 41) {
			imageName = imageLink.substring(41);
			File f = ImageUltiFunctions.getFileFromUri(Global.getURI(imageName));
			if (f != null) {
				Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(f, 500, 500);
				iv.setImageBitmap(b);
			} else {
				iv.setImageResource(R.drawable.trip1);
			}
		} else {
			iv.setImageResource(R.drawable.trip1);
		}

		return myContentsView;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		// TODO Auto-generated method stub
		return null;
	}

}
