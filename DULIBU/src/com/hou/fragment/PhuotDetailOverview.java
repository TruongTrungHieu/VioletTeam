package com.hou.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import java.io.File;
import java.util.ArrayList;

import com.hou.adapters.PhuotDetailCommentAdapter;
import com.hou.app.Global;
import com.hou.dulibu.R;
import com.hou.dulibu.R.layout;
import com.hou.model.PhuotDetailComment;
import com.hou.ultis.ImageUltiFunctions;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PhuotDetailOverview extends Fragment {
	TextView tvPhuotName, tvGhiChu, tvDiaChi, tvMaDiaPhuong, tvTrangThaiChuan;
	ArrayList<PhuotDetailComment> cmtArr = new ArrayList<PhuotDetailComment>();
	ListView lvComment;
	Button btnPostCmt;
	ImageView im,ivRecommended;
	EditText txtComment;
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.phuot_detail_overview,container,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        tvPhuotName = (TextView)v.findViewById(R.id.tvPhuotName);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
        tvPhuotName.setTypeface(tf);
        tvGhiChu = (TextView)v.findViewById(R.id.tvGhiChu);
        tvTrangThaiChuan = (TextView)v.findViewById(R.id.tvTrangThaiChuan);
        im = (ImageView) v.findViewById(R.id.ivPhuotImage);
        ivRecommended = (ImageView) v.findViewById(R.id.ivRecommended);
        btnPostCmt = (Button) v.findViewById(R.id.btnPostCmt);
        txtComment = (EditText) v.findViewById(R.id.txtComment);
        btnPostCmt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtComment.setText("");
				Toast.makeText(getActivity(), "Bình luận của bạn đang chờ kiểm duyệt...", Toast.LENGTH_LONG);
			}
		});
        
    /*    Bundle bundlePDO = getArguments();
        if(bundlePDO!=null){
        	String maDiemPhuot = bundlePDO.getString("maDiemPhuot");   
        	String tenDiemPhuot = bundlePDO.getString("tenDiemPhuot");   
        	Log.e("QViet",tenDiemPhuot);
            tvPhuotName.setText(bundlePDO.getString("tenDiemPhuot"));
            tvGhiChu.setText(bundlePDO.getString("ghiChu"));
          //  TXT.setText(d);
        }*/
        
        Context context = getActivity().getApplicationContext();
        
		
        
        String maDiemPhuot = com.hou.app.Global.getPreference(context,"maDiemPhuot","Viet");
        String tenDiemPhuot = com.hou.app.Global.getPreference(context,"tenDiemPhuot","Viet");
        String latDiemPhuot = com.hou.app.Global.getPreference(context,"lat_diemphuot","Viet");
        String lonDiemPhuot = com.hou.app.Global.getPreference(context,"lon_diemphuot","Viet");
        String ghiChu = com.hou.app.Global.getPreference(context,"ghiChu","Viet");
        String image = com.hou.app.Global.getPreference(context,"imagePhuot","Viet");
        String trangThaiChuan = com.hou.app.Global.getPreference(context,"trangThaiChuan","Viet");
       // String tenDiemPhuot = sharedPreferences.getString("tenDiemPhuot",null);
        Log.e("NQViet","Ghi LinhAnh:" + image );
        tvPhuotName.setText(tenDiemPhuot);
        tvGhiChu.setText(ghiChu);
        if(trangThaiChuan.equals("1")){
        	ivRecommended.setImageResource(R.drawable.icon_tick);
        }
        else{
        	ivRecommended.setImageResource(R.drawable.icon_admin);
        }
        String imageLink = image;
        Log.e("VietNQ","Link anh: " + imageLink);
		String imageNameDetail;
		Log.e("__imageLink",imageLink+"");
		if (imageLink.length() > 41) {
			imageNameDetail = imageLink.substring(41);
			
			Log.e("__imageNameDetail",imageNameDetail+"");
			File f = ImageUltiFunctions.getFileFromUri(Global.getURI(imageNameDetail));
			if (f != null) {
				Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(f, 500, 500);
				im.setImageBitmap(b);
			} else {
				im.setImageResource(R.drawable.trip1);
			}
		} else {
			im.setImageResource(R.drawable.trip1);
		}
      
        
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
        //lvComment.setAdapter(adapter);
        return v;
    }
}
