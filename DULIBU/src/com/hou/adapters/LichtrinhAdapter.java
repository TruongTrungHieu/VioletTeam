package com.hou.adapters;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.hou.app.Global;
import com.hou.dulibu.R;
import com.hou.model.Lichtrinh;
import com.hou.ultis.ImageUltiFunctions;
import com.hou.upload.ImageDownloader;
import com.hou.upload.MD5;

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

public class LichtrinhAdapter extends ArrayAdapter<Lichtrinh> {

	Activity context = null;
	ArrayList<Lichtrinh> myArray = null;
	int layoutId;

	public LichtrinhAdapter(Activity context, int layoutId,
			ArrayList<Lichtrinh> arr) {
		super(context, layoutId, arr);
		this.context = context;
		this.layoutId = layoutId;
		this.myArray = arr;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		convertView = inflater.inflate(layoutId, null);
		//
		if (myArray.size() > 0 && position >= 0) {

			final TextView txtTripTitle = (TextView) convertView
					.findViewById(R.id.titleTrip);
			final TextView txtTripTimeBegin = (TextView) convertView
					.findViewById(R.id.tvTimeBeginTrip);
			final TextView txtTripTimeEnd = (TextView) convertView
					.findViewById(R.id.tvTimeEndTrip);
			// final TextView txtTripNumberMember = (TextView)
			// convertView.findViewById(R.id.tvCountMember);
			final TextView txtBeginPoint = (TextView) convertView
					.findViewById(R.id.tvBeginPoint);
			final TextView txtEndPoint = (TextView) convertView
					.findViewById(R.id.tvEndPoint);

			final Lichtrinh sTrip = myArray.get(position);

			txtTripTitle.setText(sTrip.getTenLichtrinh().toString());
			txtTripTimeBegin.setText(sTrip.getTgBatdau().toString());
			txtTripTimeEnd.setText(sTrip.getTgKetthuc().toString());
			txtBeginPoint.setText(sTrip.getDiemBatdau().toString());
			txtEndPoint.setText(sTrip.getDiemKetthuc().toString());

			final ImageView imgTrip = (ImageView) convertView
					.findViewById(R.id.imgTrip);

			try {
				File f = ImageUltiFunctions.getFileFromUri(Global
						.getURI(new MD5().getMD5(sTrip.getImage())));
				if (f != null) {
					Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(
							f, 500, 500);
					imgTrip.setImageBitmap(b);
				} else {
					imgTrip.setImageResource(R.drawable.trip1);
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return convertView;
	}
}