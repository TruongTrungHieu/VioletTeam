package com.hou.fragment;

import java.util.ArrayList;

import org.w3c.dom.ls.LSSerializer;

import com.hou.dulibu.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.hou.adapters.MyArrayAdapterPlace;
import com.hou.adapters.MyPlace;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;

public class TripDetailTripForUser extends Fragment{
	//setHasOptionsMenu(true);
	ArrayAdapter adapter;
	ArrayList<MyPlace> lstPlace;
	private SlidingUpPanelLayout mLayout;
	ImageView imgSlide;
	@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.trip_detail_trip_for_user,container,false);
        
        imgSlide = (ImageView) v.findViewById(R.id.imgSlide);
        final ListView lvSlide = (ListView) v.findViewById(R.id.lvSlide);
        MyPlace lungcu = new MyPlace("Cột Cờ Lũng cú", "Đồng văn - Hà Giang", "khó", "trip6");
        MyPlace mucangchai = new MyPlace("Mù căng chải", "Mù căng chải - Yên bái", "Dễ", "trip2");
        MyPlace mapileng = new MyPlace("Đèo Mã pí lèng", "Đồng văn - Hà Giang", "Rất khó", "trip3");
        MyPlace hamlon = new MyPlace("Núi Hàm lợn", "Sóc sơn - Hà Nội", "Dễ", "trip4");
        MyPlace tamdao = new MyPlace("Tam đảo", "Tam đảo - Vĩnh Phúc", "Dễ", "trip5");
        MyPlace thanhthuy = new MyPlace("Cửa Khẩu Thanh thủy", "Thanh thủy - Hà Giang", "Dễ", "trip6");
        MyPlace banha = new MyPlace("Ruộng bậc thang Ba nhà", "Mù căng chải - Yên bái", "Dễ", "trip1");
        MyPlace dinhthuhovuong = new MyPlace("Dinh thự họ Vương", "Đồng văn - Hà Giang", "khó", "trip2");
        
        lstPlace = new ArrayList<MyPlace>();
        lstPlace.add(lungcu);
        lstPlace.add(mucangchai);
        lstPlace.add(mapileng);
        lstPlace.add(hamlon);
        lstPlace.add(tamdao);
        lstPlace.add(thanhthuy);
        lstPlace.add(banha);
        lstPlace.add(dinhthuhovuong);
        
        adapter = new MyArrayAdapterPlace(getActivity(),
				R.layout.list_row_slide, lstPlace);
		lvSlide.setAdapter(adapter);
		
		mLayout = (SlidingUpPanelLayout) v.findViewById(R.id.sliding_layout);
		mLayout.setPanelSlideListener(new PanelSlideListener() {
			@Override
			public void onPanelSlide(View panel, float slideOffset) {
//				Log.i(TAG, "onPanelSlide, offset " + slideOffset);
			}

			@Override
			public void onPanelExpanded(View panel) {
//				Log.i(TAG, "onPanelExpanded");
				imgSlide.setImageResource(R.drawable.slide_down);
			}

			@Override
			public void onPanelCollapsed(View panel) {
//				Log.i(TAG, "onPanelCollapsed");
				imgSlide.setImageResource(R.drawable.slide_up);
			}

			@Override
			public void onPanelAnchored(View panel) {
//				Log.i(TAG, "onPanelAnchored");
			}

			@Override
			public void onPanelHidden(View panel) {
//				Log.i(TAG, "onPanelHidden");
			}
		});
        
        
        
        return v;
    }
}
