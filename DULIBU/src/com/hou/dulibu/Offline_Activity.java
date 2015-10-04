package com.hou.dulibu;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.hou.adapters.KhoanChiArrayAdapter;
import com.hou.adapters.SuKienAdapter;
import com.hou.model.Chitieu;
import com.hou.model.Sukien;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Offline_Activity extends ActionBarActivity {
	

	final Context context = this;
	ArrayList<Sukien> arrSuKien = new ArrayList<Sukien>();
	SuKienAdapter adapter = null;
	ListView lvSukien = null;
//	GoogleMap map;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offline);


		lvSukien = (ListView) findViewById(R.id.lvMap);
		arrSuKien = new ArrayList<Sukien>();

		adapter = new SuKienAdapter(this, R.layout.sukien_item,
				arrSuKien);
		lvSukien.setAdapter(adapter);
	}
//	 public class MapListAdapter extends BaseAdapter {
//
//	        ArrayList<Location> myLocations;
//	        Context mContext;
//
//	        MapListAdapter(Context context) {
//	            mContext = context;
//
//	            myLocations = new ArrayList<Location>();
//	            myLocations.add(new Location(0,"Brazil",-14.235004,-51.925280));
//	            myLocations.add(new Location(1,"Africa",8.783195,34.508523));
//	            myLocations.add(new Location(2,"GERMANY",51.165691,10.451526));
//	        }
//
//	        @Override
//	        public int getCount() {
//	            return myLocations.size();
//	        }
//
//	        @Override
//	        public Object getItem(int position) {
//	            return myLocations.get(position);
//	        }
//
//	        @Override
//	        public long getItemId(int position) {
//	            return position;
//	        }
//
//	        @Override
//	        public View getView(int position, View convertView, ViewGroup parent) {
//	            if ( convertView == null )
//	                convertView = new CustomItem(mContext,myLocations.get(position));
//
//	            return convertView;
//	        }
//	    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.offline_, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add) {
			xulynhap();
	//		Toast.makeText(getApplicationContext(), "Kick ADD", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	private void xulynhap() {
		Sukien sk = new Sukien();
		sk.setTenSukien("96 Dinh Cong");
		//kc.setTienKhoanChi(tien+"");
		//Toast.makeText(getApplicationContext(), soTien+"", Toast.LENGTH_SHORT).show();
		sk.setThoigian("16h");		
		
		arrSuKien.add(sk);
		
//		LatLng latLng=new LatLng(-14.235004,-51.925280);
//		map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

		
	//	updateTien1(0);
		

		adapter.notifyDataSetChanged();
		
	}
}
