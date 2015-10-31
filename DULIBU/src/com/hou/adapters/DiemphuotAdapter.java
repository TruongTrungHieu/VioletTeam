package com.hou.adapters;

import java.io.File;
import java.util.List;

import com.hou.app.Global;
import com.hou.dulibu.R;
import com.hou.model.Diemphuot;
import com.hou.ultis.ImageUltiFunctions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DiemphuotAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	private List<Diemphuot> phuots;
	private static LayoutInflater inflater = null;
//	private String ImageUrl;
	
	

	public DiemphuotAdapter(Context context, List<Diemphuot> phuots) {
		this.context = context;
		this.phuots = phuots;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return phuots.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint({ "InflateParams", "ViewHolder" }) @Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		v = inflater.inflate(R.layout.list_phuot_item, null);
		
		ImageView im = (ImageView) v.findViewById(R.id.ivPhuot);
		TextView tv_name = (TextView) v.findViewById(R.id.tvPhuotName);
		TextView tv_address = (TextView) v.findViewById(R.id.tvAddress);
		
		String imageLink = phuots.get(position).getImage();
		String imageName;
		if (imageLink.length() > 41) {
			imageName = imageLink.substring(41);
			File f = ImageUltiFunctions.getFileFromUri(Global.getURI(imageName));
			if (f != null) {
				Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(f, 500, 500);
				im.setImageBitmap(b);
			} else {
				im.setImageResource(R.drawable.trip1);
			}
		} else {
			im.setImageResource(R.drawable.trip1);
		}
		tv_name.setText(((Diemphuot) phuots.get(position)).getTenDiemphuot());
		tv_address.setText(((Diemphuot) phuots.get(position)).getDiachi());
		
		return v;
	}

}
