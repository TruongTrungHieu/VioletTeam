package com.hou.dulibu;

import android.support.v4.app.DialogFragment;

import com.google.android.gms.maps.GoogleMap;
import com.hou.app.Global;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ChangeMap extends DialogFragment {

	private RadioGroup rg;
	private RadioButton rdNormal, rdSate;
	private Button btnOk, btnCancel;
	private int maptype;
	private GoogleMap googleMap;
	private Context c;

	public ChangeMap(GoogleMap gm, Context c, int maptype) {
		// TODO Auto-generated constructor stub
		this.googleMap = gm;
		this.c = c;
		this.maptype = maptype;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(R.style.dialogFragment, R.style.dialogFragment);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.change_map_activity, container);
		rg = (RadioGroup) v.findViewById(R.id.rg);
		rdNormal = (RadioButton) v.findViewById(R.id.radioBtnNormal);
		rdSate = (RadioButton) v.findViewById(R.id.radioBtnSatellite);
		btnOk = (Button) v.findViewById(R.id.btnMapOk);
		btnCancel = (Button) v.findViewById(R.id.btnMapCancel);

		if (maptype == GoogleMap.MAP_TYPE_NORMAL) {
			rdNormal.setChecked(true);
		}
		if (maptype == GoogleMap.MAP_TYPE_SATELLITE) {
			rdSate.setChecked(true);
		}

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setMaptype();
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thoat();
			}
		});
		return v;
	}

	private void thoat() {
		this.dismiss();
	}

	private void setMaptype() {
		int mapChecked = rg.getCheckedRadioButtonId();
		switch (mapChecked) {
		case R.id.radioBtnNormal:
			maptype = GoogleMap.MAP_TYPE_NORMAL;
			googleMap.setMapType(maptype);
			Global.saveIntPreference(c, "mapType", maptype);
			this.dismiss();
			break;
		case R.id.radioBtnSatellite:
			maptype = GoogleMap.MAP_TYPE_SATELLITE;
			googleMap.setMapType(maptype);
			Global.saveIntPreference(c, "mapType", maptype);
			this.dismiss();
		}
	}

}