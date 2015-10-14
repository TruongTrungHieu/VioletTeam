package com.hou.fragment;

import java.util.ArrayList;

import com.hou.adapters.DiemphuotAdapter;
import com.hou.adapters.LichtrinhViewPagerAdapter;
import com.hou.app.Global;
import com.hou.dulibu.DeviceStatus;
import com.hou.dulibu.PhuotDetailManager;
import com.hou.dulibu.R;
import com.hou.dulibu.TripDetailManagerActivity;
import com.hou.model.Diemphuot;
import com.hou.sliding_tab.LichTrinhSlidingTabLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class ListPhuotFragment extends Fragment {
	ProgressDialog pDialog;
	ArrayList<Diemphuot> phuots;
	ListView lvListTrip;
	GridView gv_phuot;
	
	LichTrinhSlidingTabLayout tabs;
	//Toolbar toolbar;
	LichtrinhViewPagerAdapter adapter;
	ViewPager pager;
	CharSequence Titles[] = { "Táº¥t cáº£", "Má»›i", "Tá»‰nh-TP", "Ä�á»™ khÃ³" };
	int Numboftabs = 4;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//toolbar = (Toolbar) findViewById(R.id.tool_bar);
		//setSupportActionBar(toolbar);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_phuot_manager, container, false);
		/*
		 * final ListView lvListTrip = (ListView)
		 * view.findViewById(R.id.lvTripList); DeviceStatus ds = new
		 * DeviceStatus();
		 * 
		 * Diemphuot phuot1 = new Diemphuot("phuot1", "MÃ¹ Cang Cháº£i", "lat",
		 * "long", "YB", "YÃªn BÃ¡i", "note", "phuot1.jpg", "KhÃ³ Ä‘áº¥y :v",
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
		
		
		//slydingtab
		/*adapter = new LichtrinhViewPagerAdapter(getChildFragmentManager(), Titles, Numboftabs);

		// Assigning ViewPager View and setting the adapter
		pager = (ViewPager) view.findViewById(R.id.pager);
		pager.setAdapter(adapter);

		// Assiging the Sliding Tab Layout View
		tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
		tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true,
										// This makes the tabs Space Evenly in
										// Available width

		// Setting Custom Color for the Scroll bar indicator of the Tab View
		tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
			@Override
			public int getIndicatorColor(int position) {
				return getResources().getColor(R.color.StatusBarColor);
			}
		});

		// Setting the ViewPager For the SlidingTabsLayout
		tabs.setViewPager(pager);*/
		
		
		if (Global.LIST_DIEMPHUOT.isEmpty()) {
			Diemphuot phuot1 = new Diemphuot("phuot1", "MÃ¹ Cang Cháº£i", "lat", "long", "YB", "YÃªn BÃ¡i", "MÃ¹ Cang Cháº£i lÃ  1 huyá»‡n vÃ¹ng cao cá»§a tá»‰nh YÃªn BÃ¡i, cÃ¡ch HÃ  Ná»™i khoáº£ng 280 Km, ná»•i tiáº¿ng vá»›i danh tháº¯ng ruá»™ng báº­c thang",
					"phuot1.jpg", 1);
			Global.LIST_DIEMPHUOT.add(phuot1);
			Diemphuot phuot2 = new Diemphuot("phuot2", "Há»“ GÆ°Æ¡m", "lat", "long", "HN", "HÃ  Ná»™i", "note",
					"phuot1.jpg", 0);
			Global.LIST_DIEMPHUOT.add(phuot2);
			Diemphuot phuot3 = new Diemphuot("phuot3", "Khoa CNTT - Viá»‡n Ä�áº¡i há»�c Má»Ÿ HÃ  Ná»™i", "lat", "long", "HN", "HÃ  Ná»™i", "note",
					"phuot1.jpg", 1);
			Global.LIST_DIEMPHUOT.add(phuot3);
			Diemphuot phuot4 = new Diemphuot("phuot4", "Ä�áº£o Sinh Tá»“n", "lat", "long", "TS", "TrÆ°á»�ng Sa", "note",
					"phuot1.jpg", 1);
			Global.LIST_DIEMPHUOT.add(phuot4);
			Diemphuot phuot5 = new Diemphuot("phuot5", "Ä�áº£o SÆ¡n Ca", "lat", "long", "TS", "TrÆ°á»�ng Sa", "note",
					"phuot1.jpg", 1);
			Global.LIST_DIEMPHUOT.add(phuot5);
			Diemphuot phuot6 = new Diemphuot("phuot6", "Ä�áº£o SÆ¡n Ca", "lat", "long", "TS", "TrÆ°á»�ng Sa", "note",
					"phuot1.jpg", 1);
			Global.LIST_DIEMPHUOT.add(phuot6);
			Diemphuot phuot7 = new Diemphuot("phuot7", "Ä�áº£o SÆ¡n Ca", "lat", "long", "TS", "TrÆ°á»�ng Sa", "note",
					"phuot1.jpg", 1);
			Global.LIST_DIEMPHUOT.add(phuot7);
			Diemphuot phuot8 = new Diemphuot("phuot8", "Ä�áº£o SÆ¡n Ca", "lat", "long", "TS", "TrÆ°á»�ng Sa", "note",
					"phuot1.jpg", 1);
			Global.LIST_DIEMPHUOT.add(phuot8);
			Diemphuot phuot9 = new Diemphuot("phuot9", "Ä�áº£o SÆ¡n Ca", "lat", "long", "TS", "TrÆ°á»�ng Sa", "note",
					"phuot1.jpg", 1);
			Global.LIST_DIEMPHUOT.add(phuot9);
			Diemphuot phuot10 = new Diemphuot("phuot10", "Ä�áº£o SÆ¡n Ca", "lat", "long", "TS", "TrÆ°á»�ng Sa", "note",
					"phuot1.jpg", 1);
			Global.LIST_DIEMPHUOT.add(phuot10);
			Diemphuot phuot11 = new Diemphuot("phuot11", "Ä�áº£o SÆ¡n Ca", "lat", "long", "TS", "TrÆ°á»�ng Sa", "note",
					"phuot1.jpg", 1);
			Global.LIST_DIEMPHUOT.add(phuot11);
			Diemphuot phuot12 = new Diemphuot("phuot12", "Ä�áº£o SÆ¡n Ca", "lat", "long", "TS", "TrÆ°á»�ng Sa", "note",
					"phuot1.jpg", 1);
			Global.LIST_DIEMPHUOT.add(phuot12);
			Diemphuot phuot13 = new Diemphuot("phuot13", "Ä�áº£o SÆ¡n Ca", "lat", "long", "TS", "TrÆ°á»�ng Sa", "note",
					"phuot1.jpg", 1);
			Global.LIST_DIEMPHUOT.add(phuot13);
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
		gv_phuot.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "" + phuots.get(position).getGhichu(), Toast.LENGTH_SHORT).show();
				/*ListPhuotFragment f = new ListPhuotFragment();
				Bundle b = new Bundle();
				b.putString("maDiemPhuot", phuots.get(position).getMaDiemphuot());
				b.putString("tenDiemPhuot", phuots.get(position).getTenDiemphuot());
				b.putString("ghiChu", phuots.get(position).getGhichu());
				b.putString("trangThaiChuan", phuots.get(position).getTrangthaiChuan());
				f.setArguments(b);*/
				Intent intent = new Intent(getActivity(), PhuotDetailManager.class);
				/*intent.putExtra("maDiemPhuot", phuots.get(position).getMaDiemphuot());
				intent.putExtra("tenDiemPhuot", phuots.get(position).getTenDiemphuot());
				intent.putExtra("ghiChu", phuots.get(position).getGhichu());
				intent.putExtra("trangThaiChuan", phuots.get(position).getTrangthaiChuan());*/
				startActivity(intent);
			}
		});
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
