package com.hou.fragment;

import com.hou.dulibu.ChiTieu_Activity;
import com.hou.dulibu.Offline_Activity;
import com.hou.dulibu.R;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TripDetailInfoForUser extends Fragment implements OnClickListener{
	TextView tvKinhPhiForUser, tvBtnOfflineForUser;
	Button btnJoinUser, btnLeaveUser;

	@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.trip_detail_info_for_user,container,false);
        findViewById(v);
        tvKinhPhiForUser.setOnClickListener(this);
        btnJoinUser.setOnClickListener(this);
        btnLeaveUser.setOnClickListener(this);
        tvBtnOfflineForUser.setOnClickListener(this);
        return v;
    }
	
	public void findViewById(View v){
		tvKinhPhiForUser = (TextView) v.findViewById(R.id.tvKinhPhiForUser);
		btnJoinUser = (Button) v.findViewById(R.id.btnJoinForUser);
		btnLeaveUser = (Button) v.findViewById(R.id.btnLeaveForUser);
		tvBtnOfflineForUser = (TextView) v.findViewById(R.id.tvBtnOfflineForUser);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tvKinhPhiForUser:
			Intent i = new Intent(getActivity(),ChiTieu_Activity.class);
			startActivity(i);
			break;
		case R.id.btnJoinForUser:
			btnJoinUser.setVisibility(View.INVISIBLE);
			btnLeaveUser.setVisibility(View.VISIBLE);
			Toast.makeText(getActivity(), "join", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnLeaveForUser:
			btnJoinUser.setVisibility(View.VISIBLE);
			btnLeaveUser.setVisibility(View.INVISIBLE);
			Toast.makeText(getActivity(), "leave", Toast.LENGTH_SHORT).show();
			break;
		case R.id.tvBtnOfflineForUser:
			Intent offline = new Intent(getActivity(),Offline_Activity.class);
			startActivity(offline);
			break;
			//Toast.makeText(getActivity(), "Offline Events", Toast.LENGTH_SHORT).show();
		default:
			break;
		} 
	}
}
