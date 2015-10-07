package com.hou.fragment;


import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.hou.adapters.MemberAdapter;
import com.hou.dulibu.ChiTieu_Activity;
import com.hou.dulibu.R;
import com.hou.model.Lichtrinh_User;

public class TripDetailMemberActivity extends Fragment  {
//	final Activity context = this;

	ArrayList<Lichtrinh_User> arrListMember = new ArrayList<Lichtrinh_User>();
	MemberAdapter adapter = null;
	ListView lvMember = null;
	
	@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.trip_detail_member,container,false);
        findViewById(v);
        arrListMember = new ArrayList<Lichtrinh_User>();
        for(int i = 0 ; i<20;i++)
        {
        	Lichtrinh_User a = new Lichtrinh_User();
        	a.setMaUser("MS001");
        	a.setTrangthai_ketnoi("Online");
        	a.setTrangthai_antoan("Save");
        	arrListMember.add(a);
        }
        

		adapter = new MemberAdapter(getActivity(), R.layout.member_item,
				arrListMember);
		lvMember.setAdapter(adapter);
		lvMember.setOnItemClickListener(new OnItemClickListener() {

		

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int positon,
					long arg3) {
				Toast.makeText(getActivity(), "item : " + positon, Toast.LENGTH_SHORT).show();
			}
		});
	
        return v;
    }
	public void findViewById(View v){
		 lvMember = (ListView) v.findViewById(R.id.lv_member);
	}

}