package com.hou.dulibu;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.hou.adapters.KhoanChiArrayAdapter;
import com.hou.app.Global;

import com.hou.model.Chitieu;
import com.hou.model.Diemphuot;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class ChiTieu_Activity extends ActionBarActivity {
	final Context context = this;

	ArrayList<Chitieu> arrKhoanChi = new ArrayList<Chitieu>();
	ArrayList<Chitieu> arrChitieu = new ArrayList<Chitieu>();
	KhoanChiArrayAdapter adapter = null;
	ListView lvKhoanchi = null;
	TextView txtConDu;
	double tongTien=1000;
	int conDu ;
	int moiNguoi = 0;
	int khoanChi = 1;
	String ten, tien;
	String maLichTrinh = "562b1962bcb17fde6e619360";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chi_tieu);
		
        
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		txtConDu = (TextView) findViewById(R.id.txtConDu);		
		lvKhoanchi = (ListView) findViewById(R.id.lvChiTietTieu);
		arrKhoanChi = new ArrayList<Chitieu>();
		//getChiPhi(com.hou.app.Global.getPreference(context,"maLichTrinh","Viet"));
		getChiPhi(maLichTrinh);
		/*arrKhoanChi = arrChitieu;
		adapter = new KhoanChiArrayAdapter(this, R.layout.list_itemdetail,
				arrKhoanChi);
		lvKhoanchi.setAdapter(adapter);*/
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
	public void loadData(){
		arrKhoanChi = arrChitieu;
		adapter = new KhoanChiArrayAdapter(this, R.layout.list_itemdetail,
				arrKhoanChi);
		lvKhoanchi.setAdapter(adapter);
	}

	public void getChiPhi(String idTrip) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", idTrip);
		params.put("access_token",
				Global.getPreference(this, Global.USER_ACCESS_TOKEN, ""));
		
		client.get(Global.BASE_URI + "/" + Global.URI_GETCHIPHI_PATH , params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("getChiPhi999", response);
						// Toast.makeText(getApplicationContext(),
						// "v�o th�nh c�ng", Toast.LENGTH_SHORT).show();\
						listChiPhi(response);
						loadData();
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						Log.e("LayChiPhi",content);

					}
				});
	}
//	private String maChitieu;
//	private String maLichtrinh;
//	private String tenChitieu;
//	private String thoigian;
//	private double sotien;
//	private String filedinhkem;
//	private String maUser;
	private String listChiPhi(String response) {
	 Log.e("__Viet",response);
		
		try {
			JSONArray arrObj = new JSONArray(response);
			for (int i = 0; i < arrObj.length(); i++) {
				JSONObject chiPhiJson = arrObj.getJSONObject(i);
				Chitieu chitieu = new Chitieu();

				String _id = chiPhiJson.optString("_id");
				double sotien = chiPhiJson.optDouble("total");
				String tenChitieu = chiPhiJson.optString("name");
				String thoiGian = chiPhiJson.optString("created_at");
			//	String 
				JSONObject objectUser = chiPhiJson
						.getJSONObject("created_by");
				String maUser = objectUser.optString("_id");
				


				chitieu.setMaChitieu(_id);
				chitieu.setSotien(sotien);
				chitieu.setTenChitieu(tenChitieu);
				chitieu.setThoigian(thoiGian);
				chitieu.setFiledinhkem("");
				chitieu.setMaUser(maUser);
				chitieu.setMaLichtrinh(com.hou.app.Global.getPreference(context,maLichTrinh,"Viet"));
				arrChitieu.add(chitieu);
				Log.e("listChiPhiVIet", sotien+"");
				Toast.makeText(context, "Muc thu("+i +"): " + sotien, Toast.LENGTH_SHORT).show();
			}

			// Toast.makeText(getApplicationContext(), "KQ JSON",
			// Toast.LENGTH_LONG).show();

			return "true";
		} catch (JSONException e) {
			e.printStackTrace();
			return "false";
			
		}
	}
	private void createChiPhi(String tripId,String name,String created_at,String total) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		params.put("trip_id", tripId);
		params.put("name", name);
		params.put("created_at", created_at);
		params.put("total", total);
		

		client.post(Global.BASE_URI + "/" + Global.URI_POSTCHIPHI_PATH +"?access_token="+ Global.getPreference(this, Global.USER_ACCESS_TOKEN, ""),
				params, new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("createNewTrip", response);

						if (executeWhenRegisterSuccess(response)) {
							Toast.makeText(getApplicationContext(),
									"Tao moi chi phi thanh cong",
									Toast.LENGTH_SHORT).show();
							// Intent intent = new Intent(
							// RegisterManagerActivity.this,
							// LoginManagerActivity.class);

						} else {
							Toast.makeText(getApplicationContext(),
									"Khong tao moi duoc chi phi",
									Toast.LENGTH_LONG).show();
						}
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						Log.d("CongTien", content);
					}
				});
	}
	private boolean executeWhenRegisterSuccess(String reponse) {
		boolean check = true;
		return check;
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
					if (ten.equals("") || tien.equals("")) {						
						return;
					}
					else {
						updateTien1();
					}
					
					dialog.dismiss();
				}

				private void xulyNhap() {

					final EditText txtTen = (EditText) dialog
							.findViewById(R.id.txtTenMucChi);
					final EditText txtTien = (EditText) dialog
							.findViewById(R.id.txtSoTien);
					
					double soTien;
					ten = txtTen.getText().toString();
					tien = txtTien.getText().toString();
					
					if (ten.equals("") || tien.equals("")) {
						Toast.makeText(getApplication(),
								"Ban chua dien du thong tin", Toast.LENGTH_SHORT)
								.show();
						return;
					}
					soTien = Double.parseDouble(tien);
					Chitieu kc = new Chitieu();
					kc.setTenChitieu(ten);
					kc.setSotien(soTien);					
					arrKhoanChi.add(kc);
					/*SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy ");
					String day = sdf.format(new Date());*/
					
					createChiPhi(maLichTrinh,ten,"2015-10-10 10:10:10",tien);
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


