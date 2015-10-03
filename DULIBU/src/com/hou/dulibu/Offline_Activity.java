package com.hou.dulibu;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class Offline_Activity extends ActionBarActivity {
	
	ListView myListOfMaps;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offline);

		myListOfMaps = (ListView) findViewById(R.id.mapList);
        myListOfMaps.setAdapter( new MapListAdapter(this) );

	}
	 public class MapListAdapter extends BaseAdapter {

	        ArrayList<Location> myLocations;
	        Context mContext;

	        MapListAdapter(Context context) {
	            mContext = context;

	            myLocations = new ArrayList<Location>();
	            myLocations.add(new Location(0,"Brazil",-14.235004,-51.925280));
	            myLocations.add(new Location(1,"Africa",8.783195,34.508523));
	            myLocations.add(new Location(2,"GERMANY",51.165691,10.451526));
	        }

	        @Override
	        public int getCount() {
	            return myLocations.size();
	        }

	        @Override
	        public Object getItem(int position) {
	            return myLocations.get(position);
	        }

	        @Override
	        public long getItemId(int position) {
	            return position;
	        }

	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	            if ( convertView == null )
	                convertView = new CustomItem(mContext,myLocations.get(position));

	            return convertView;
	        }
	    }


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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
