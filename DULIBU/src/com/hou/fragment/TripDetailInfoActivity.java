package com.hou.fragment;


import com.hou.dulibu.ChiTieu_Activity;
import com.hou.dulibu.Offline_Activity;
import com.hou.dulibu.R;
import com.hou.dulibu.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TripDetailInfoActivity extends Fragment implements OnClickListener{
	TextView tvKinhPhi, tvBtnOffline;

	@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.trip_detail_info,container,false);
        findViewById(v);
        tvKinhPhi.setOnClickListener(this);
        return v;
    }
	
	public void findViewById(View v){
		tvKinhPhi = (TextView) v.findViewById(R.id.tvKinhPhi);
		tvBtnOffline = (TextView) v.findViewById(R.id.tvBtnOffline);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tvKinhPhi:
			Intent i = new Intent(getActivity(),ChiTieu_Activity.class);
			startActivity(i);
			break;
		case R.id.tvBtnOffline:
			Intent offline = new Intent(getActivity(),Offline_Activity.class);
			startActivity(offline);
			break;
	//		Toast.makeText(getActivity(), "Offline Events Viet day", Toast.LENGTH_SHORT).show();
		default:
			break;
		} 
	}
}
