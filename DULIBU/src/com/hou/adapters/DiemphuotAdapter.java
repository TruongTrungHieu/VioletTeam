package com.hou.adapters;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.hou.app.Global;
import com.hou.dulibu.R;
import com.hou.model.Diemphuot;
import com.hou.ultis.ImageUltiFunctions;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DiemphuotAdapter extends BaseAdapter {
	Context context;
	List<Diemphuot> phuots;
	private LayoutInflater inflater = null;
	Bitmap bmp;
	String ImageUrl;

	public DiemphuotAdapter(Context context, List<Diemphuot> phuots) {
		this.context = context;
		this.phuots = phuots;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		Holder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_phuot_item, parent,
					false);
			holder = new Holder();
			holder.initView(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.setData(phuots.get(position));
		ImageUrl = ((Diemphuot) phuots.get(position)).getImage();

		// Toast.makeText(context, "" + phuots.get(position).getMaDiemphuot(),
		// Toast.LENGTH_SHORT);
		// im.setBackgroundResource(((Place) phuots.get(position)).getImage());
		// File f =
		// ImageUltiFunctions.getFileFromUri(Global.getURI(phuots.get(position).getMaDiemphuot())
		// + ".jpg");
		/*
		 * File f =
		 * ImageUltiFunctions.getFileFromUri(Global.getURI(phuots.get(position
		 * ).getImage())); if (f != null) { Bitmap b =
		 * ImageUltiFunctions.decodeSampledBitmapFromFile(f, 500, 500);
		 * im.setImageBitmap(b); } else {
		 */
		//new LoadImageDiemPhuot().execute();
		/* } */
		/*
		 * else { im.setImageResource(R.drawable.trip1); }
		 */

		
		return convertView;
	}

	public class Holder implements OnClickListener {
		TextView tv_name;
		TextView tv_address;
		ImageView ivPhuot;

		public Holder() {
			super();
		}

		public void initView(View view) {
			tv_name = (TextView) view.findViewById(R.id.tvPhuotName);
			ivPhuot = (ImageView) view.findViewById(R.id.ivPhuot);
			tv_address = (TextView) view.findViewById(R.id.tvAddress);
			ivPhuot.setOnClickListener(this);
		}

		public void setData(Diemphuot dp) {
			tv_name.setText(dp.getTenDiemphuot());
			tv_address.setText(dp.getDiachi());
			new LoadImageDiemPhuot().execute();
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.ivPhuot:
				
				break;

			default:
				break;
			}
		}
		
		class LoadImageDiemPhuot extends AsyncTask<String, integer, String> {

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				try {
					URL url = new URL(ImageUrl);
					bmp = BitmapFactory.decodeStream(url.openConnection()
							.getInputStream());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				ivPhuot.setImageBitmap(bmp);
			}
		}
	}	

}

/*
 * public void startDiemDL(int position) { Intent intent = new Intent(context,
 * DiemdulichActivity.class); intent.putExtra("maDiemDL",
 * phuots.get(position).getMaDiemDL()); context.startActivity(intent); }
 */

