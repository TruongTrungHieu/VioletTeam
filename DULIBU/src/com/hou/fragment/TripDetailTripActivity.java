package com.hou.fragment;

import java.util.ArrayList;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.GoogleMap;
import com.hou.dulibu.R;
import com.hou.dulibu.R.layout;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class TripDetailTripActivity extends Fragment {
	GoogleMap mMap;
	ImageView imgMapPlace, imgMapWarnning, imgMapTeam, imgMapHospital,
			imgMapGas;
	int status = 0; // 0b
	Boolean stSlide = true;

	ArrayList<ImageView> lstImg;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.trip_detail_trip, container, false);

		lstImg = new ArrayList<ImageView>();
		lstImg.add(imgMapPlace);
		lstImg.add(imgMapWarnning);
		lstImg.add(imgMapTeam);
		lstImg.add(imgMapHospital);
		lstImg.add(imgMapGas);

		imgMapPlace = (ImageView) v.findViewById(R.id.imgMapPlace);
		imgMapWarnning = (ImageView) v.findViewById(R.id.imgMapWarnning);
		imgMapTeam = (ImageView) v.findViewById(R.id.imgMapTeam);
		imgMapHospital = (ImageView) v.findViewById(R.id.imgMapHospital);
		imgMapGas = (ImageView) v.findViewById(R.id.imgMapGas);
		imgMapPlace.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				status ^= 16; // 1 << 4
				// mMap.clear();
				// onMapReady(mMap);
				if (((status >> 4) & 1) == 0) {
					imgMapPlace.setImageResource(R.drawable.map_place);
				} else {
					imgMapPlace.setImageResource(R.drawable.map_place1);
				}

			}
		});
		imgMapWarnning.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				status ^= 1;
				// mMap.clear();
				// onMapReady(mMap);
				if (((status >> 0) & 1) == 0) {
					imgMapWarnning.setImageResource(R.drawable.map_warnning);
				} else {
					imgMapWarnning.setImageResource(R.drawable.map_warnning1);
				}
			}
		});
		imgMapTeam.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				status ^= 4;
				// mMap.clear();
				// onMapReady(mMap);
				if (((status >> 2) & 1) == 0) {
					imgMapTeam.setImageResource(R.drawable.map_team);
				} else {
					imgMapTeam.setImageResource(R.drawable.map_team1);
				}

			}
		});
		imgMapHospital.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				status ^= 2;
				// mMap.clear();
				// onMapReady(mMap);
				if (((status >> 1) & 1) == 0) {
					imgMapHospital.setImageResource(R.drawable.map_hospital);
				} else {
					imgMapHospital.setImageResource(R.drawable.map_hospital1);
				}

			}
		});
		imgMapGas.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				status ^= 8;
				// mMap.clear();
				// onMapReady(mMap);
				if (((status >> 3) & 1) == 0) {
					imgMapGas.setImageResource(R.drawable.map_gas);
				} else {
					imgMapGas.setImageResource(R.drawable.map_gas1);
				}
			}
		});
		FixWidthBottom(imgMapPlace, imgMapWarnning, imgMapTeam, imgMapHospital,
				imgMapGas);

		return v;

	}

	public void CheckStatusBottom() {

		ArrayList<ImageView> lstImg = new ArrayList<ImageView>();

		lstImg.add(imgMapWarnning);
		lstImg.add(imgMapHospital);
		lstImg.add(imgMapTeam);
		lstImg.add(imgMapGas);
		lstImg.add(imgMapPlace);

		for (int i = lstImg.size() - 1; i >= 0; i--) {
			lstImg.get(i).setAlpha((((status >> i) & 1) == 0 ? 1f : 0.5f));
		}

	}

	// icon nÃ o Ä‘ang Ä‘Æ°á»£c chá»�n
	public void ImgIsSelected() {
		if (((status >> 4) & 1) == 1) {
			// place

		}
		if (((status >> 2) & 1) == 1) {
			// warnning
		}
		if (((status >> 1) & 1) == 1) {
			// team
		}
		if (((status >> 3) & 1) == 1) {
			// hospital
		}
		if (((status >> 0) & 1) == 1) {
			// gas
		}
	}

	// cÄƒn chá»‰nh khi thay Ä‘á»•i kÃ­ch thÆ°á»›c mÃ n hÃ¬nh
	public void FixWidthBottom(ImageView place, ImageView warnning,
			ImageView team, ImageView hospital, ImageView gas) {
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		ArrayList<ImageView> arrIv = new ArrayList<ImageView>();
		arrIv.add(place);
		arrIv.add(warnning);
		arrIv.add(team);
		arrIv.add(hospital);
		arrIv.add(gas);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;

		if (true) {
			int t = (width - arrIv.size() * 60) / (6);
			int b = t;

			for (ImageView imageView : arrIv) {
				RelativeLayout.LayoutParams arrImageBottom = new RelativeLayout.LayoutParams(
						imageView.getLayoutParams());
				arrImageBottom.setMargins(t, 0, 0, 0);
				imageView.setLayoutParams(arrImageBottom);
				t = t + 60 + b;
			}
		}
	}
}