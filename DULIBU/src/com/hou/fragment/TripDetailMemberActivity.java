package com.hou.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.hou.adapters.MemberBeforeStartAdapter;
import com.hou.adapters.MemberStartAdapter;
import com.hou.dulibu.ChiTieu_Activity;
import com.hou.dulibu.R;
import com.hou.dulibu.UserSecureConfirmManager;
import com.hou.model.Lichtrinh_User;

public class TripDetailMemberActivity extends Fragment {
	// final Activity context = this;

	ArrayList<Lichtrinh_User> arrListMember = new ArrayList<Lichtrinh_User>();
	MemberBeforeStartAdapter adapter0 = null;
	MemberStartAdapter adapter1 = null;
	ListView lvMember = null;
	Button btnChange;
	int trangthai;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.trip_detail_member, container, false);
		findViewById(v);
		trangthai = 1;
		arrListMember = new ArrayList<Lichtrinh_User>();
		for (int i = 0; i < 20; i++) {
			Lichtrinh_User a = new Lichtrinh_User();
			a.setMaUser("MS001_"+i);
			a.setTrangthai_ketnoi("Online");
			a.setTrangthai_antoan("Safe");
			arrListMember.add(a);
		}
		setAdapterListView(getActivity());
		btnChange = (Button) v.findViewById(R.id.btnTest);
		btnChange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (trangthai == 0) {
					trangthai = 1;
				} else {
					trangthai = 0;
				}
				setAdapterListView(getActivity());
			}
		});


		return v;
	}
	public void setAdapterListView(final Activity c){
		if (trangthai == 0) {
			adapter0 = new MemberBeforeStartAdapter(c, R.layout.member_item, arrListMember);
			lvMember.setAdapter(adapter0);
			/*lvMember.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int positon, long arg3) {
					// Toast.makeText(getActivity(), "item : " + positon,
					// Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(c, UserSecureConfirmManager.class);
					startActivity(intent);
				}
			});*/
		} else {
			adapter1 = new MemberStartAdapter(c, R.layout.trip_detail_member_after_start, arrListMember);
			lvMember.setAdapter(adapter1);
			lvMember.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int positon, long arg3) {
					// Toast.makeText(getActivity(), "item : " + positon,
					// Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(c, UserSecureConfirmManager.class);
					startActivity(intent);
				}
			});
		}
	}

	public void findViewById(View v) {
		lvMember = (ListView) v.findViewById(R.id.lv_member);
	}

}