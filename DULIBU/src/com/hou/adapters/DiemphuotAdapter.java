package com.hou.adapters;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DiemphuotAdapter extends BaseAdapter {
	Context context;
	List<Diemphuot> phuots;
	List<Bitmap> bitmaps;
	private static LayoutInflater inflater = null;
	Bitmap bmp;
	String ImageUrl;
	ImageView im;
	

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
		
		im = (ImageView) v.findViewById(R.id.ivPhuot);

		TextView tv_name = (TextView) v.findViewById(R.id.tvPhuotName);
		TextView tv_address = (TextView) v.findViewById(R.id.tvAddress);
		// ImageUrl = ((Diemphuot)phuots.get(position)).getImage();

		// Toast.makeText(context, "" + phuots.get(position).getMaDiemphuot(),
		// Toast.LENGTH_SHORT);
		// im.setBackgroundResource(((Place) phuots.get(position)).getImage());
		// File f =
		// ImageUltiFunctions.getFileFromUri(Global.getURI(phuots.get(position).getMaDiemphuot())
		// + ".jpg");
		// File f =
		// ImageUltiFunctions.getFileFromUri(Global.getURI(phuots.get(position).getImage()));
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
		

//		v.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Toast.makeText(context, "Tên: " + ((Diemphuot) phuots.get(position)).getTenDiemphuot() + "\n"
//						+ "id ảnh: " + ((Diemphuot) phuots.get(position)).getImage(), Toast.LENGTH_LONG).show();
//			}
//		});

		return v;
	}

	/*
	 * class LoadImageDiemPhuot extends AsyncTask<String, integer, String> {
	 * 
	 * @Override protected String doInBackground(String... params) { // TODO
	 * Auto-generated method stub try { URL url = new URL(ImageUrl); bmp =
	 * BitmapFactory.decodeStream(url.openConnection().getInputStream()); }
	 * catch (MalformedURLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); }
	 * 
	 * return null; }
	 * 
	 * @Override protected void onPostExecute(String result) { // TODO
	 * Auto-generated method stub super.onPostExecute(result);
	 * im.setImageBitmap(bmp); } }
	 */
	class LoadImageDiemPhuot extends AsyncTask<String, integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				for (Diemphuot dp : phuots) {
					URL url = new URL(dp.getImage());
					bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
					bitmaps.add(bmp);
				}
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
			im.setImageBitmap(bmp);
		}
	}
}
/*
 * public void startDiemDL(int position) { Intent intent = new Intent(context,
 * DiemdulichActivity.class); intent.putExtra("maDiemDL",
 * phuots.get(position).getMaDiemDL()); context.startActivity(intent); }
 */
