package com.hou.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

import com.hou.adapters.PhuotDetailCommentAdapter;
import com.hou.dulibu.R;
import com.hou.dulibu.R.layout;
import com.hou.model.PhuotDetailComment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class PhuotDetailOverview extends Fragment {
	TextView tvPhuotName, tvGhiChu, tvDiaChi, tvMaDiaPhuong, tvTrangThaiChuan;
	ArrayList<PhuotDetailComment> cmtArr = new ArrayList<PhuotDetailComment>();
	ListView lvComment;
	@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.phuot_detail_overview,container,false);
        tvPhuotName = (TextView)v.findViewById(R.id.tvPhuotName);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
        tvPhuotName.setTypeface(tf);
        /*tvGhiChu = (TextView)v.findViewById(R.id.tvGhiChu);
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
        
        
        if(cmtArr.isEmpty()){
			PhuotDetailComment cmt1 = new PhuotDetailComment("cmt1", "Great!", "10:00 am 09/29/2015");
			cmtArr.add(cmt1);
			PhuotDetailComment cmt2 = new PhuotDetailComment("cmt2", "Cool!", "09:00 am 09/29/2015");
			cmtArr.add(cmt2);
			PhuotDetailComment cmt3 = new PhuotDetailComment("cmt3", "Nice!", "08:00 am 09/29/2015");
			cmtArr.add(cmt3);
		}
        PhuotDetailCommentAdapter adapter = new PhuotDetailCommentAdapter(getActivity(), R.layout.phuot_detail_overview_comment_item, cmtArr);
        lvComment = (ListView) v.findViewById(R.id.lvComments);
        lvComment.setAdapter(adapter);
        return v;
    }
}
