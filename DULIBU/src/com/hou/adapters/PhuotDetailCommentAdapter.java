package com.hou.adapters;

import java.util.ArrayList;

import com.hou.dulibu.R;
import com.hou.model.PhuotDetailComment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PhuotDetailCommentAdapter extends ArrayAdapter<PhuotDetailComment> {
	Activity context = null;
	ArrayList<PhuotDetailComment> myArray = null;
	int layoutId;

	public PhuotDetailCommentAdapter(Activity context, int layoutId, ArrayList<PhuotDetailComment> arr) {
		super(context, layoutId, arr);
		this.context = context;
		this.layoutId = layoutId;
		this.myArray = arr;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		convertView = inflater.inflate(layoutId, null);

		if (myArray.size() > 0 && position >= 0) {

			final TextView txtComment = (TextView) convertView.findViewById(R.id.tvUserComment);
			final TextView txtTTime = (TextView) convertView.findViewById(R.id.tvUserCommentTime);

			final PhuotDetailComment sCmt = myArray.get(position);

			txtComment.setText(sCmt.getComment().toString());
			txtTTime.setText(sCmt.getTime().toString());
			// txtTripNumberMember.setText(sTrip.getTripNumberMember().toString());

			/*final ImageView imgAvarta = (ImageView) convertView.findViewById(R.id.ivUserAvarta);

			Resources res = context.getResources();
			String imgName = "" + sTrip.getMaLichtrinh();
			int resID = res.getIdentifier(imgName, "drawable", context.getPackageName());
			Drawable drawable = res.getDrawable(resID);
			imgTrip.setImageDrawable(drawable);*/
		}
		return convertView;
	}
}
