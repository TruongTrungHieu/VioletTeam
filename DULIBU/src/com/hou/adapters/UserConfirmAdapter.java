package com.hou.adapters;

import java.util.ArrayList;

import com.hou.dulibu.R;
import com.hou.model.UserConfirm;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserConfirmAdapter extends ArrayAdapter<UserConfirm> {
	Activity context = null;
	ArrayList<UserConfirm> myArray = null;
	int layoutId;
	public UserConfirmAdapter(Activity context, int layoutid, ArrayList<UserConfirm> arr){
		super(context, layoutid, arr);
		this.context = context;
		this.layoutId = layoutid;
		this.myArray = arr;
	}
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		convertView = inflater.inflate(layoutId, null);
		
		if (myArray.size() > 0 && position >= 0) {
			final TextView txtName = (TextView) convertView.findViewById(R.id.tvUserConfirmName);
			final ImageView ivStt = (ImageView) convertView.findViewById(R.id.ivUserConfirmStt);
			
			final UserConfirm uc = myArray.get(position);
			
			txtName.setText(uc.getTenUser().toString());
			Resources res = context.getResources();
			if(uc.getSttConfirm() == 1){
				Drawable drawable = res.getDrawable(R.drawable.ic_visible);
				ivStt.setScaleType(ImageView.ScaleType.FIT_XY);
				ivStt.setImageDrawable(drawable);
			}
			else{
				Drawable drawable = res.getDrawable(R.drawable.ic_visible_no);
				ivStt.setScaleType(ImageView.ScaleType.FIT_XY);
				ivStt.setImageDrawable(drawable);
			}
		}
		
		return convertView;
	}
}
