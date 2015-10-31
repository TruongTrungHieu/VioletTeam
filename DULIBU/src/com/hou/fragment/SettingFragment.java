package com.hou.fragment;

import com.hou.dulibu.Change_Password_Activity;
import com.hou.dulibu.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SettingFragment extends Fragment {
	private TextView change_password,dieukhoan;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.setting_layout, container, false);
		change_password = (TextView) view.findViewById(R.id.changepass);
	    change_password.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						Change_Password_Activity.class);
				startActivity(intent);
			}
		});
	    dieukhoan = (TextView) view.findViewById(R.id.txtThongtindieukhoan);
	    dieukhoan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog();
			}
		});
		return view;
	}
	public void showDialog() {
		final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dieukhoan_dialog, null);
		dialog.setView(view);
		dialog.show();
	}
	
}
