package com.hou.adapters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.hou.app.Global;
import com.hou.dulibu.PhuotDetailOverview;
import com.hou.dulibu.R;
import com.hou.model.Diemphuot;
import com.hou.model.Lichtrinh;
import com.hou.ultis.ImageUltiFunctions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DiemphuotAdapter extends BaseAdapter {
	Context context;
	List<Diemphuot> phuots;
	private static LayoutInflater inflater = null;

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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		v = inflater.inflate(R.layout.list_phuot_item, null);

		ImageView im = (ImageView) v.findViewById(R.id.ivPhuot);

		TextView tv_name = (TextView) v.findViewById(R.id.tvPhuotName);
		TextView tv_address = (TextView) v.findViewById(R.id.tvAddress);
		TextView tv_hardlevel = (TextView) v.findViewById(R.id.tvHardLevel);

		//Toast.makeText(context, "" + phuots.get(position).getMaDiemphuot(), Toast.LENGTH_SHORT);
		// im.setBackgroundResource(((Place) phuots.get(position)).getImage());
		//File f = ImageUltiFunctions.getFileFromUri(Global.getURI(phuots.get(position).getMaDiemphuot()) + ".jpg");
		File f = ImageUltiFunctions.getFileFromUri(Global.getURI(phuots.get(position).getImage()));
		if (f != null) {
			Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(f, 500, 500);
			im.setImageBitmap(b);

		} else {
			im.setImageResource(R.drawable.trip1);
		}

		tv_name.setText(((Diemphuot) phuots.get(position)).getTenDiemphuot());
		tv_address.setText(((Diemphuot) phuots.get(position)).getDiachi());
//		tv_hardlevel.setText(((Diemphuot) phuots.get(position)).getDokho());
		/*Toast.makeText(context, "ID diem: " + ((Diemphuot) phuots.get(position)).getDokho(),
				Toast.LENGTH_SHORT).show();*/
		/*v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// startDiemDL(position);
				Toast.makeText(context, "ID diem: " + ((Diemphuot) phuots.get(position)).getMaDiemphuot(),
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(context, PhuotDetailOverview.class);
				sta
			}
		});*/

		return v;
	}

	/*
	 * public void startDiemDL(int position) { Intent intent = new
	 * Intent(context, DiemdulichActivity.class); intent.putExtra("maDiemDL",
	 * phuots.get(position).getMaDiemDL()); context.startActivity(intent); }
	 */
}
