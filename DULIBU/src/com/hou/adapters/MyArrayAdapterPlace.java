package com.hou.adapters;

import java.util.ArrayList;

import com.hou.dulibu.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapterPlace extends ArrayAdapter<MyPlace> {
	Activity context;
	ArrayList<MyPlace> myArray = null;
	int layoutId;

	public MyArrayAdapterPlace(Activity context, int layoutId,
			ArrayList<MyPlace> arr) {
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
			
			final MyPlace mpl = myArray.get(position);
			
			Resources res = context.getResources();
			int resID = res.getIdentifier(mpl.getImgPath() , "drawable", context.getPackageName());
			Drawable drawable = res.getDrawable(resID );
			
			txtDiachi.setText(mpl.getDiachi().toString());
			txtTitle.setText(mpl.getTitle().toString());
			txtLoaihinh.setText(mpl.getDokho().toString());
			thumbnail.setImageDrawable(drawable);
		}

		return convertView;
	}

}
