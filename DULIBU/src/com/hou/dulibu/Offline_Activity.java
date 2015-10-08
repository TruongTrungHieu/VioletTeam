package com.hou.dulibu;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hou.adapters.SuKienAdapter;
import com.hou.model.Sukien;

public class Offline_Activity extends ActionBarActivity {

	final Context context = this;
	ArrayList<Sukien> arrSuKien = new ArrayList<Sukien>();
	SuKienAdapter adapter = null;
	ListView lvSukien = null;

	// GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offline);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		lvSukien = (ListView) findViewById(R.id.lvMap);
		arrSuKien = new ArrayList<Sukien>();

		adapter = new SuKienAdapter(this, R.layout.sukien_item, arrSuKien);
		lvSukien.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.offline_, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id == R.id.home){
			onBackPressed();
		}
		if (id == R.id.action_add) {
			// Toast.makeText(getApplication(),"Select form add Chi tiet",
			// Toast.LENGTH_SHORT).show();
			final Dialog dialog = new Dialog(context);
			dialog.setContentView(R.layout.dialog_sukien);
			dialog.setTitle(getString(R.string.title_activity_offline_));
			
	    
			Button btnOK = (Button) dialog.findViewById(R.id.btnAdd);
			// if button is clicked, close the custom dialog
			btnOK.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					xulyNhap();
					Toast.makeText(getApplication(), "Click on button ADD",
							Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}

				private void xulyNhap() {

					final EditText txtTenSuKien = (EditText) dialog
							.findViewById(R.id.txtTenSuKien);
					final EditText txtThoigian = (EditText) dialog
							.findViewById(R.id.txtThoiGian);
					final EditText txtDiadiem = (EditText) dialog
							.findViewById(R.id.txtDiadiem);
					String ten, thoigian, diadiem;
					ten = txtTenSuKien.getText().toString();
					thoigian = txtThoigian.getText().toString();
					diadiem = txtDiadiem.getText().toString();
					
					if (ten.equals("") || thoigian.equals("") || diadiem.equals("")) {
						Toast.makeText(getApplication(),
								"Ban dien thieu thong tin", Toast.LENGTH_SHORT)
								.show();
						return;
					}
					
					Sukien sk = new Sukien();
					sk.setTenSukien(ten);
					sk.setThoigian(thoigian);
					sk.setDiadiem(diadiem);
//					Sukien sk = new Sukien();
//					sk.setTenSukien("96 Dinh Cong");
//					sk.setThoigian("16h");
//					sk.setDiadiem("96 dinh cong");


					arrSuKien.add(sk);

					// LatLng latLng=new LatLng(-14.235004,-51.925280);
					// map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

					adapter.notifyDataSetChanged();
					
					//dialog.dismiss();

				}
			});
			Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
			btnCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
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
