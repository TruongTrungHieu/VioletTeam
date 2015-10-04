package com.hou.dulibu;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;


import com.hou.adapters.KhoanChiArrayAdapter;

import com.hou.model.Chitieu;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.renderscript.Sampler.Value;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class ChiTieu_Activity extends ActionBarActivity {
	final Context context = this;

	ArrayList<Chitieu> arrKhoanChi = new ArrayList<Chitieu>();
	KhoanChiArrayAdapter adapter = null;
	ListView lvKhoanchi = null;
	TextView txtConDu;
	int tongTien=1000;
	int conDu ;
	int moiNguoi = 0;
	int khoanChi = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chi_tieu);
		

//		ActionBar actionBar = getActionBar();
////
////		actionBar.setDisplayHomeAsUpEnabled(true);
////		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.setTitle("TÄ‚Âªn");
//		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//		actionBar.setDisplayHomeAsUpEnabled(true);
		
		
	
		
		
//		actionBar.setTitle("Chi tiáº¿t kinh phĂ­");
//		

		txtConDu = (TextView) findViewById(R.id.txtConDu);
		
		
		lvKhoanchi = (ListView) findViewById(R.id.lvChiTietTieu);
		arrKhoanChi = new ArrayList<Chitieu>();

		adapter = new KhoanChiArrayAdapter(this, R.layout.list_itemdetail,
				arrKhoanChi);
		lvKhoanchi.setAdapter(adapter);
		lvKhoanchi.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int positon, long arg3) {
				PopupMenu popup = new PopupMenu(ChiTieu_Activity.this,lvKhoanchi);
				
				popup.getMenuInflater().inflate(R.menu.chitieu_popup,
						popup.getMenu());
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						updateTien(positon);
						 arrKhoanChi.remove(positon);
						 
						 
						 adapter.notifyDataSetChanged();
						return false;
					}

				});
				// arrKhoanChi.remove(positon);
				// adapter.notifyDataSetChanged();
				 popup.show();
				return false;
			}
		});
	}

	public void updateTien1(int position){
		//int khoanChi = 0;
		String a="";
		String b="";
		double mucchi=0;
		for(int i=0;i>=arrKhoanChi.size();i++){
//			KhoanChi a = new KhoanChi();
//			a = arrKhoanChi.get(i);
			//khoanChi = arrKhoanChi.get(0).getTien();
	//		Toast.makeText(getApplicationContext(), i, Toast.LENGTH_SHORT).show();
		}
	//	Toast.makeText(getApplicationContext(), khoanChi+"", Toast.LENGTH_SHORT).show();
			
		a = arrKhoanChi.get(0).getSotien() + ""; // Khi them phan tu moi vao thi arraylist add phan tu do vao dau day
//		KhoanChi c = new KhoanChi();
		int d = arrKhoanChi.size()-1;
//		c = arrKhoanChi.get(d);
		mucchi = arrKhoanChi.get(d).getSotien();
		Toast.makeText(getApplicationContext(), mucchi+"", Toast.LENGTH_SHORT).show();
		//conDu = tongTien - mucchi;
		tongTien -= mucchi;
		txtConDu.setText(tongTien+"");
	//	txtConDu.setText(khoanChi+"");
		
	}
	public void updateTien(int position){
//		for(int i = 0;i >=arrKhoanChi.size();i++)
//		{
//			khoanChi = khoanChi + arrKhoanChi.get(i).getTien();
//			
//		}
//		conDu = tongTien - khoanChi;
//		txtConDu.setText(conDu+"");
		double mucchi;
		
		mucchi = arrKhoanChi.get(position).getSotien();
		Toast.makeText(getApplicationContext(), mucchi+"", Toast.LENGTH_SHORT).show();
		tongTien += mucchi;
		txtConDu.setText(tongTien+"");
		//conDu = tongTien + mucchi;
		//txtConDu.setText(conDu+"");
//		String b ="";
//		b = arrKhoanChi.get(position).getTien() +"";
//		txtConDu.setText(conDu+"");
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chitieu_menu, menu);
	
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add) {
			// Toast.makeText(getApplication(),"Select form add Chi tiet",
			// Toast.LENGTH_SHORT).show();
			final Dialog dialog = new Dialog(context);
			dialog.setContentView(R.layout.dialog_khoanchi);
			dialog.setTitle("Thông tin khoản chi");
			
			//dialog.setTitle("Title Khoan Chi ");

			// final EditText txtTen = (EditText) dialog
			// .findViewById(R.id.editText1);
			// final EditText txtTien = (EditText) dialog
			// .findViewById(R.id.editText2);

			Button btnOK = (Button) dialog.findViewById(R.id.btnAdd);
			// if button is clicked, close the custom dialog
			btnOK.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					// Toast.makeText(
					// getApplication(),
					// txtTen.getText().toString()
					// + txtTien.getText().toString(),
					// Toast.LENGTH_SHORT).show();

					xulyNhap();
					dialog.dismiss();
				}

				private void xulyNhap() {

					final EditText txtTen = (EditText) dialog
							.findViewById(R.id.txtTenMucChi);
					final EditText txtTien = (EditText) dialog
							.findViewById(R.id.txtSoTien);
					String ten, tien;
					double soTien;
					ten = txtTen.getText().toString();
					tien = txtTien.getText().toString();
					
					if (ten.equals("") || tien.equals("")) {
						Toast.makeText(getApplication(),
								"Ă„Â�iĂ¡Â»Â�u thiĂ¡ÂºÂ¿u thÄ‚Â´ng tin", Toast.LENGTH_SHORT)
								.show();
						return;
					}
					soTien = Double.parseDouble(tien);
					Chitieu kc = new Chitieu();
					kc.setTenChitieu(ten);
					//kc.setTienKhoanChi(tien+"");
					//Toast.makeText(getApplicationContext(), soTien+"", Toast.LENGTH_SHORT).show();
					kc.setSotien(soTien);					
					arrKhoanChi.add(kc);
					
					updateTien1(0);

					adapter.notifyDataSetChanged();
					dialog.dismiss();

				}
			});
			Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
			btnCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplication(), "Click on button Cancel",
							Toast.LENGTH_SHORT).show();
				//	updateTien(1);
					dialog.dismiss();
				}
			});

			// return true;
			dialog.show();
		}
//		if (id == R.id.action_add) {
//			
//		}
		return super.onOptionsItemSelected(item);
	}
}


