package com.hou.adapters;

import java.io.File;
import java.util.ArrayList;

import com.hou.app.Global;
import com.hou.dulibu.R;
import com.hou.model.Diemphuot;
import com.hou.ultis.ImageUltiFunctions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapterPlace extends ArrayAdapter<Diemphuot> {
	Activity context;
	ArrayList<Diemphuot> myArray = null;
	int layoutId;

	public MyArrayAdapterPlace(Activity context, int layoutId,
			ArrayList<Diemphuot> arr) {
		super(context, layoutId, arr);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.layoutId = layoutId;
		this.myArray = arr;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		convertView = inflater.inflate(layoutId, null);

		if (myArray.size() > 0 && position >= 0) {
			final ImageView thumbnail = (ImageView) convertView
					.findViewById(R.id.thumbnail);
			final TextView txtTitle = (TextView) convertView
					.findViewById(R.id.title);
			final TextView txtLoaihinh = (TextView) convertView
					.findViewById(R.id.dokho);
			final TextView txtDiachi = (TextView) convertView
					.findViewById(R.id.diachi);
			
			final Diemphuot mpl = myArray.get(position);
			
			/*Resources res = context.getResources();
			int resID = res.getIdentifier(mpl.getImage() , "drawable", context.getPackageName());
			Drawable drawable = res.getDrawable(resID );*/
			String imageLink = myArray.get(position).getImage();
			String imageName;
			if (imageLink.length() > 41) {
				imageName = imageLink.substring(41);
				File f = ImageUltiFunctions.getFileFromUri(Global.getURI(imageName));
				if (f != null) {
					Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(f, 500, 500);
					thumbnail.setImageBitmap(b);
				} else {
					thumbnail.setImageResource(R.drawable.trip1);
				}
			} else {
				thumbnail.setImageResource(R.drawable.trip1);
			}
			
			txtDiachi.setText(mpl.getMaDiemphuot().toString());
			txtTitle.setText(mpl.getTenDiemphuot().toString());
			//txtLoaihinh.setText(mpl.getDokho().toString());
			//thumbnail.setImageDrawable(drawable);
		}

		return convertView;
	}

}
