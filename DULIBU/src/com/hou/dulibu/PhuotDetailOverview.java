package com.hou.dulibu;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PhuotDetailOverview extends Fragment {
	TextView tvPhuotName, tvGhiChu, tvDiaChi, tvMaDiaPhuong, tvTrangThaiChuan;
	@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.phuot_detail_overview,container,false);
        /*tvPhuotName = (TextView)v.findViewById(R.id.tvPhuotName);
        tvGhiChu = (TextView)v.findViewById(R.id.tvGhiChu);
        tvTrangThaiChuan = (TextView)v.findViewById(R.id.tvDoKho);
        
        Bundle b = this.getArguments();
        
        tvPhuotName.setText(b.getString("tenDiemPhuot"));
        tvGhiChu.setText(b.getString("ghiChu"));
        if(b.getString("trangThaiChuan")=="1"){
        	tvTrangThaiChuan.setText("Recommended");
        }
        else{
        	tvTrangThaiChuan.setText("Chưa xác minh");
        }*/
        return v;
    }
}
