package com.hou.fragment;

import com.hou.dulibu.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class ThongTinUngDung extends Fragment implements OnClickListener{
	TextView tvAppName, tvTerms;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater
				.inflate(R.layout.thong_tin_ung_dung, container, false);
		tvAppName = (TextView) view.findViewById(R.id.tvTitleName);		
		
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Thin.ttf");
		tvAppName.setTypeface(tf);
		return view;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		default:
			break;
		}
	}
	 public void showDialog(){
		 final Dialog dialog = new Dialog(getActivity());
         dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
         dialog.setContentView(R.layout.dieukhoan_dialog);
         dialog.show();
    }
}
