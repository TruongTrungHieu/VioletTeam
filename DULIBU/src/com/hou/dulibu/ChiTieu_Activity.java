package com.hou.dulibu;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.text.DecimalFormat;
import java.util.ArrayList;


import com.hou.adapters.KhoanChiArrayAdapter;

import com.hou.model.Chitieu;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.DatePicker;
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
	double tongTien=1000;
	int conDu ;
	int moiNguoi = 0;
	int khoanChi = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chi_tieu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
				 popup.show();
				return false;
			}
		});
	}

	public void updateTien1(){
		double mucchi=0;
		int d = arrKhoanChi.size()-1;
		mucchi = arrKhoanChi.get(d).getSotien();
		tongTien -= mucchi;
		DecimalFormat df = new DecimalFormat("#.#");
		txtConDu.setText(df.format(tongTien));
		
	}
	public void updateTien(int position){
			double mucchi;
			mucchi = arrKhoanChi.get(position).getSotien();
			tongTien += mucchi;
			DecimalFormat df = new DecimalFormat("#.#");
			txtConDu.setText(df.format(tongTien));
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chitieu_menu, menu);
	
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == android.R.id.home){
			onBackPressed();
			Toast.makeText(getBaseContext(), "ok back", Toast.LENGTH_SHORT).show();
		}
		if (id == R.id.action_add) {
			final Dialog dialog = new Dialog(context);
			dialog.setContentView(R.layout.dialog_khoanchi);
			dialog.setTitle(R.string.titleChiPhiDialog);

			Button btnOK = (Button) dialog.findViewById(R.id.btnAdd);
			// if button is clicked, close the custom dialog
			btnOK.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					xulyNhap();
					updateTien1();
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
								"Chưa nhập đủ thông tin", Toast.LENGTH_SHORT)
								.show();
						return;
					}
					soTien = Double.parseDouble(tien);
					Chitieu kc = new Chitieu();
					kc.setTenChitieu(ten);
					kc.setSotien(soTien);					
					arrKhoanChi.add(kc);
					adapter.notifyDataSetChanged();
					dialog.dismiss();

				}
			});
			Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
			btnCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(getApplication(), "Click on button Cancel",
							Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}
			});

			dialog.show();
		}
		return super.onOptionsItemSelected(item);
	}
	
}


