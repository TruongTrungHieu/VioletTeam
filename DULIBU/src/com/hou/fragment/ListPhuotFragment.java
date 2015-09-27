package com.hou.fragment;

import java.util.ArrayList;

import com.hou.adapters.DiemphuotAdapter;
import com.hou.app.Global;
import com.hou.dulibu.DeviceStatus;
import com.hou.dulibu.R;
import com.hou.dulibu.TripDetailManagerActivity;
import com.hou.model.Diemphuot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class ListPhuotFragment extends Fragment {
	ProgressDialog pDialog;
	ArrayList<Diemphuot> phuots;
	ListView lvListTrip;
	GridView gv_phuot;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_phuot_manager, container, false);
		/*
		 * final ListView lvListTrip = (ListView)
		 * view.findViewById(R.id.lvTripList); DeviceStatus ds = new
		 * DeviceStatus();
		 * 
		 * Diemphuot phuot1 = new Diemphuot("phuot1", "Mù Cang Chải", "lat",
		 * "long", "YB", "Yên Bái", "note", "phuot1.jpg", "Khó đấy :v",
		 * "Recommended"); diemphuot = new ArrayList<Diemphuot>();
		 * diemphuot.add(phuot1); DiemphuotAdapter adapter = new
		 * DiemphuotAdapter(getActivity(), R.layout.list_phuot_item, diemphuot);
		 * lvListTrip.setAdapter(adapter); lvListTrip.setOnItemClickListener(new
		 * android.widget.AdapterView.OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) { // TODO Auto-generated method stub
		 * 
		 * Toast.makeText(getActivity(), "" +
		 * diemphuot.get(position).getMadiemphuot(), Toast.LENGTH_SHORT).show();
		 * Intent intent = new Intent(getActivity(),
		 * TripDetailManagerActivity.class); startActivity(intent); } });
		 */
		if (Global.LIST_DIEMPHUOT.isEmpty()) {
			Diemphuot phuot1 = new Diemphuot("phuot1", "Mù Cang Chải", "lat", "long", "YB", "Yên Bái", "note",
					"phuot1.jpg", "Recommended");
			Global.LIST_DIEMPHUOT.add(phuot1);
		}

		// showSlideImage(SlideImageArr);

		initGridView(view);

		return view;

	}

	void initGridView(View view) {
		gv_phuot = (GridView) view.findViewById(R.id.gvPhuotGrid);

		phuots = new ArrayList<Diemphuot>();
		Diemphuot diemphuot;

		for (int i = 0; i < Global.LIST_DIEMPHUOT.size(); ++i) {
			diemphuot = new Diemphuot();
			diemphuot.setMaDiemphuot(Global.LIST_DIEMPHUOT.get(i).getMaDiemphuot());
			diemphuot.setTenDiemphuot(Global.LIST_DIEMPHUOT.get(i).getTenDiemphuot());
			diemphuot.setLat(Global.LIST_DIEMPHUOT.get(i).getLat());
			diemphuot.setLon(Global.LIST_DIEMPHUOT.get(i).getLon());
			diemphuot.setMaTinh(Global.LIST_DIEMPHUOT.get(i).getMaTinh());
			diemphuot.setDiachi(Global.LIST_DIEMPHUOT.get(i).getDiachi());
			diemphuot.setGhichu(Global.LIST_DIEMPHUOT.get(i).getGhichu());
			diemphuot.setImage(Global.LIST_DIEMPHUOT.get(i).getImage());
			diemphuot.setTrangthaiChuan(Global.LIST_DIEMPHUOT.get(i).getTrangthaiChuan());

			/*
			 * diemphuot.setImage(Global.LIST_DIEMPHUOT.get(i) .getIddiem() +
			 * ".jpg");
			 */
			phuots.add(diemphuot);
		}
		gv_phuot.setAdapter(new DiemphuotAdapter(getActivity(), phuots));
	}

	int getImageId(String imageName) {
		imageName = imageName.substring(3);
		return this.getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (pDialog != null) {
			pDialog.dismiss();
		}

	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		if (pDialog != null) {
			pDialog.dismiss();
		}

	}
}
