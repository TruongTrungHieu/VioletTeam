package com.hou.dulibu;

import org.json.JSONException;
import org.json.JSONObject;

import com.hou.app.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.test.suitebuilder.TestSuiteBuilder.FailedToCreateTests;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginManagerActivity extends ActionBarActivity {
	private Button btnLogin;
	private Button btnDangky;
	private EditText edtUsername;
	private EditText edtPassword;
	private GCMService gcm;

	private String username;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_manager);

		if (getSupportActionBar() != null) {
			getSupportActionBar().hide();
		}

		edtUsername = (EditText) findViewById(R.id.edtUsername);
		edtPassword = (EditText) findViewById(R.id.edtPassword);

		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				username = edtUsername.getText().toString();
				password = edtPassword.getText().toString();
				if (username != null && edtPassword != null
						&& username.trim().length() > 0
						&& password.trim().length() > 0) {
					// send request login to server
					loginToServer();
				} else {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.validator_login),
							Toast.LENGTH_LONG).show();
				}
			}
		});

		btnDangky = (Button) findViewById(R.id.btnRegister);
		btnDangky.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.createFolderDULIBU();
				Intent intent = new Intent(LoginManagerActivity.this,
						RegisterManagerActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_manager, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void loginToServer() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("username", username);
		params.put("password", password);
		client.post(Global.BASE_URI + "/" + Global.URI_DANGNHAP_PATH, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("loginToServer", response);
						if (executeWhenLoginSuccess(response)) {
							Intent intent = new Intent(
									LoginManagerActivity.this,
									ProfileManagerActivity.class);
							startActivity(intent);
						} else {
							Toast.makeText(
									getApplicationContext(),
									getResources().getString(
											R.string.login_again),
									Toast.LENGTH_LONG).show();
						}
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						edtPassword.setText(null);
						edtUsername.setText(null);
						switch (statusCode) {
						case 200:
							Toast.makeText(getApplicationContext(), "200",
									Toast.LENGTH_LONG).show();
							break;
						case 400:
							Toast.makeText(getApplicationContext(), "400",
									Toast.LENGTH_LONG).show();
							break;
						case 403:
							Toast.makeText(getApplicationContext(), "403",
									Toast.LENGTH_LONG).show();
							break;
						case 404:
							Toast.makeText(getApplicationContext(), "404",
									Toast.LENGTH_LONG).show();
							break;
						case 503:
							Toast.makeText(getApplicationContext(), "503",
									Toast.LENGTH_LONG).show();
							break;
						default:
							break;
						}
					}
				});
	}

	private boolean executeWhenLoginSuccess(String response) {
		try {
			JSONObject userJson = new JSONObject(response);

			String _id = userJson.getString("_id");
			String access_token = userJson.getString("access_token");
			String username = userJson.getString("username");
			String fullname = userJson.getString("fullname");
			String email = userJson.getString("email");
			String ngaysinh = userJson.getString("ngaysinh");
			String sdt = userJson.getString("sdt");
			String gioitinh = userJson.getString("gioitinh");
			String sdt_lienhe = userJson.getString("sdt_lienhe");
			String ghichu = userJson.getString("ghichu");

			Global.savePreference(getApplicationContext(), Global.USER_MAUSER,
					_id);
			Global.savePreference(getApplicationContext(),
					Global.USER_USERNAME, username);
			Global.savePreference(getApplicationContext(),
					Global.USER_FULLNAME, fullname);
			Global.savePreference(getApplicationContext(), Global.USER_EMAIL,
					email);
			Global.savePreference(getApplicationContext(),
					Global.USER_NGAYSINH, ngaysinh);
			Global.savePreference(getApplicationContext(), Global.USER_SDT, sdt);
			Global.savePreference(getApplicationContext(),
					Global.USER_GIOITINH, gioitinh);
			Global.savePreference(getApplicationContext(),
					Global.USER_SDT_LIENHE, sdt_lienhe);
			// Global.savePreference(getApplicationContext(),
			// Global.USER_AVATAR, );
			Global.savePreference(getApplicationContext(), Global.USER_GHICHU,
					ghichu);
			Global.savePreference(getApplicationContext(),
					Global.USER_ACCESS_TOKEN, access_token);

			return true;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}

	}
}
