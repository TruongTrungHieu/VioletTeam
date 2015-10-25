package com.hou.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hou.dulibu.R;
import com.hou.fragment.TripDetailMemberActivity;
import com.hou.model.Chitieu;
import com.hou.model.Lichtrinh_User;
import com.hou.ultis.CircularImageView;

@SuppressLint("ResourceAsColor") public class MemberBeforeStartAdapter extends ArrayAdapter<Lichtrinh_User> {
	// adapter dùng khi hành trình chưa bắt đầu
	Activity context = null;
	ArrayList<Lichtrinh_User> myArray = null;
	int layoutId;
	ImageView ivApproval;

	public MemberBeforeStartAdapter(Activity context, int layoutId, ArrayList<Lichtrinh_User> arr) {
		super(context, layoutId, arr);
		this.context = context;
		this.layoutId = layoutId;
		this.myArray = arr;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		convertView = inflater.inflate(layoutId, null);

		if (myArray.size() > 0 && position >= 0) {

			final CircularImageView iv_avatar = (CircularImageView) convertView.findViewById(R.id.iv_avarta);
			final TextView tv_fullname = (TextView) convertView.findViewById(R.id.tv_fullname);
			/*
			 * final TextView tv_saveornot = (TextView) convertView
			 * .findViewById(R.id.tv_saveornot);
			 */
			final Lichtrinh_User member = myArray.get(position);
			if (member.getTrangthai_thamgia() == 0) {
				ivApproval.setBackgroundResource(R.drawable.ic_question_mark);
			} else {
				ivApproval.setBackgroundResource(R.drawable.ic_tick_mark);
			}
			tv_fullname.setText(member.getMaUser());
			// tv_saveornot.setText(member.getTrangthai_antoan());
		}
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(position);
			}
		});

		return convertView;

	}

	private void showDialog(final int vitri) {
		AlertDialog.Builder b = new AlertDialog.Builder(context);
		b.setTitle(R.string.dialogApprovalTitle);
		b.setCancelable(true);
		if (myArray.get(vitri).getTrangthai_thamgia() == 0) {
			b.setMessage(context.getString(R.string.textMember) + " " + myArray.get(vitri).getMaUser() + " "
					+ context.getString(R.string.textRequest0));
			b.setPositiveButton(R.string.btnAccept, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					myArray.get(vitri).setTrangthai_thamgia(1);
					notifyDataSetChanged();
					dialog.dismiss();
				}
			});
			b.setNegativeButton(R.string.btnDecline, new DialogInterface.OnClickListener() {

				@Override

				public void onClick(DialogInterface dialog, int which)

				{
					myArray.remove(vitri);
					notifyDataSetChanged();
					dialog.dismiss();
				}

			});
		} 
		else {
			b.setMessage(context.getString(R.string.textMember) + " " + myArray.get(vitri).getMaUser() + " "
					+ context.getString(R.string.textRequest1));
			b.setPositiveButton(R.string.btnCancelApproval, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			b.setNegativeButton(R.string.btnDeleteMember, new DialogInterface.OnClickListener() {

				@Override

				public void onClick(DialogInterface dialog, int which)

				{
					myArray.remove(vitri);
					notifyDataSetChanged();
					dialog.dismiss();
				}

			});
		}

		AlertDialog dialog = b.create();
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {

			@Override
			public void onShow(DialogInterface dialog) {
				// TODO Auto-generated method stub
				((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.ActionBarColor);
				((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.ActionBarColor);
			}
		});
		dialog.show();
	}
	/*
	 * private void showDialog(final int vitri){ final Dialog dialog = new
	 * Dialog(context);
	 * //dialog.setContentView(R.layout.dialog_approval_member);
	 * dialog.setTitle(R.string.dialogApprovalTitle);
	 * dialog.setCancelable(true); TextView tvMessage = (TextView)
	 * dialog.findViewById(R.id.tvDialogApprovalMember);
	 * tvMessage.setText(context.getString(R.string.textMember) +" " +
	 * myArray.get(vitri).getMaUser() +" "+
	 * context.getString(R.string.textRequest)); TextView btnAccept = (TextView)
	 * dialog.findViewById(R.id.btnAccept); btnAccept.setOnClickListener(new
	 * OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub //ivApproval.setBackgroundResource(R.drawable.ic_tick_mark);
	 * myArray.get(vitri).setTrangthai_thamgia(1); notifyDataSetChanged();
	 * dialog.dismiss(); } }); TextView btnDecline = (TextView)
	 * dialog.findViewById(R.id.btnDecline); btnDecline.setOnClickListener(new
	 * OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub myArray.remove(vitri); notifyDataSetChanged(); dialog.dismiss(); }
	 * }); dialog.show(); }
	 */
}
