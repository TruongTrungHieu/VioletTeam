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

	static class ViewHolder {
		TextView txtTripTitle, txtTripTimeBegin, txtTripTimeEnd, txtBeginPoint,
				txtEndPoint;
		ImageView imgTrip;
		Lichtrinh sTrip;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(layoutId, null);
			holder = new ViewHolder();
			holder.txtTripTitle = (TextView) convertView
					.findViewById(R.id.titleTrip);
			holder.txtTripTimeBegin = (TextView) convertView
					.findViewById(R.id.tvTimeBeginTrip);
			holder.txtTripTimeEnd = (TextView) convertView
					.findViewById(R.id.tvTimeEndTrip);
			holder.txtBeginPoint = (TextView) convertView
					.findViewById(R.id.tvBeginPoint);
			holder.txtEndPoint = (TextView) convertView
					.findViewById(R.id.tvEndPoint);
			holder.imgTrip = (ImageView) convertView.findViewById(R.id.imgTrip);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (myArray.size() > 0 && position >= 0) {

			holder.sTrip = myArray.get(position);

			holder.txtTripTitle.setText(holder.sTrip.getTenLichtrinh().toString());
			holder.txtTripTimeBegin.setText(holder.sTrip.getTgBatdau().toString());
			holder.txtTripTimeEnd.setText(holder.sTrip.getTgKetthuc().toString());
			holder.txtBeginPoint.setText(holder.sTrip.getDiemBatdau().toString());
			holder.txtEndPoint.setText(holder.sTrip.getDiemKetthuc().toString());

			try {
				File f = ImageUltiFunctions.getFileFromUri(Global
						.getURI(new MD5().getMD5(holder.sTrip.getImage())));
				if (f != null) {
					Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(
							f, 500, 500);
					holder.imgTrip.setImageBitmap(b);
				} else {
					holder.imgTrip.setImageResource(R.drawable.trip1);
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return convertView;
	}
}