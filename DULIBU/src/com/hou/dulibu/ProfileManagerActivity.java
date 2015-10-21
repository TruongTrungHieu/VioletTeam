package com.hou.dulibu;

import com.hou.app.Global;
import com.hou.fragment.ListPhuotFragment;
import com.hou.fragment.ListTripFragment;
import com.hou.fragment.MapFragment;
import com.hou.fragment.MyTrips;
import com.hou.fragment.ProfileFragment;
import com.hou.fragment.ThongTinUngDung;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;

@SuppressWarnings("rawtypes")
public class ProfileManagerActivity extends MaterialNavigationDrawer implements
		MaterialAccountListener {

	private MaterialAccount account;
	private MaterialSection<Fragment> mnuInfo, mnuMyMap, mnuMyTrip,
			mnuDiemPhuot, mnuLogout, mnuAbout, mnuLstTrip;

	@Override
	public void init(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// this.getSupportActionBar().setElevation(20);
		account = new MaterialAccount(this.getResources(), "DULIBU",
				"FITHOU-2015", R.drawable.default_avartar,
				R.drawable.default_bg);
		this.addAccount(account);
		this.disableLearningPattern();

		mnuInfo = newSection("Thông tin cá nhân", R.drawable.icon_profile,
				new ProfileFragment());
		this.addSection(mnuInfo);

		// Intent mnuLstTrip = new Intent(ProfileManagerActivity.this,
		// ListTripFragment.class);
		// mnuLstTrip.putExtra("NameIntent", "mnuLstTrip");

		this.addSection(newSection("Danh sách chuyến đi",
				R.drawable.icon_list_trip, new ListTripFragment()));

		this.addSection(newSection("Điểm phượt", R.drawable.icon_place,
				new ListPhuotFragment()));

		mnuMyTrip = newSection("Chuyến đi của tôi", R.drawable.icon_heart,
				new MyTrips());
		this.addSection(mnuMyTrip);

		mnuMyMap = newSection("Bản đồ", R.drawable.icon_map, new MapFragment());
		this.addSection(mnuMyMap);

		mnuLogout = newSection("Đăng xuất", R.drawable.icon_logout,
				new Intent(this, LoginManagerActivity.class));
		this.addSection(mnuLogout);

		mnuAbout = newSection("Thông tin ứng dụng", R.drawable.icon_about,
				new ThongTinUngDung());
		this.addSection(mnuAbout);

	}

	@Override
	public void onAccountOpening(MaterialAccount account) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChangeAccount(MaterialAccount newAccount) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_manager, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finishAffinity();
	}

	private void logoutToServer() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		String access_token = Global.getPreference(getApplicationContext(),
				Global.USER_ACCESS_TOKEN, Global.ACCESS_TOKEN_DEFAULT);

		params.put("access_token", access_token);

		client.post(Global.BASE_URI + "/" + Global.URI_DANGXUAT_PATH, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("logoutToServer", response);
						Global.savePreference(getApplicationContext(),
								Global.USER_ACCESS_TOKEN, null);

						Intent intent = new Intent(ProfileManagerActivity.this,
								LoginManagerActivity.class);
						startActivity(intent);

					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						switch (statusCode) {
						case 400:
							Toast.makeText(getApplicationContext(),
									getResources().getString(R.string.e400),
									Toast.LENGTH_LONG).show();
							break;
						case 403:
							Toast.makeText(getApplicationContext(),
									getResources().getString(R.string.e403),
									Toast.LENGTH_LONG).show();
							break;
						case 404:
							Toast.makeText(getApplicationContext(),
									getResources().getString(R.string.e404),
									Toast.LENGTH_LONG).show();
							break;
						case 503:
							Toast.makeText(getApplicationContext(),
									getResources().getString(R.string.e503),
									Toast.LENGTH_LONG).show();
							break;
						default:
							break;
						}
					}
				});
	}

}