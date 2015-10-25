package com.hou.dulibu;

import org.json.JSONException;
import org.json.JSONObject;

import com.hou.app.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("DefaultLocale") public class LoginManagerActivity extends ActionBarActivity {
	private Button btnLogin;
	private Button btnDangky;
	private EditText edtUsername;
	private EditText edtPassword;
	private String username;
	private String password;
	private TextView tvForgetPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_manager);

		if (getSupportActionBar() != null) {
			getSupportActionBar().hide();
		}

		tvForgetPassword = (TextView) findViewById(R.id.tvForgetPassword);
		edtUsername = (EditText) findViewById(R.id.edtUsername);
		edtPassword = (EditText) findViewById(R.id.edtPassword);
		tvForgetPassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ForgetPass();
			}
		});

		edtUsername.setText("thanhtunguong");
		edtPassword.setText("123456");
		
		edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int id,
					KeyEvent event) {
				if (id == R.id.login || id == EditorInfo.IME_NULL) {
					username = edtUsername.getText().toString().toLowerCase();
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
					return true;
				}
				return false;
			}
		});

		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				username = edtUsername.getText().toString().toLowerCase();
				password = edtPassword.getText().toString();
				if (username.equalsIgnoreCase("")) {
					edtUsername.setError(getString(R.string.checknull));
					edtUsername.requestFocus();
				}else {
					if (password.equalsIgnoreCase("")) {
						edtPassword.setError(getString(R.string.checknull));
						edtPassword.requestFocus();
					}else {
						loginToServer();
					}
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
//						Log.e("loginToServer", response);
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
						case 400:
							
							edtUsername.setError(getString(R.string.e401));
							edtUsername.requestFocus();
									
							break;
						case 403:
							edtUsername.setError(getString(R.string.e403));
							edtUsername.requestFocus();
							break;
						case 404:
							
							edtUsername.setError(getString(R.string.e404));
							edtUsername.requestFocus();
							break;
						case 503:
							edtUsername.setError(getString(R.string.e503));
							edtUsername.requestFocus();
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

			String _id = userJson.optString("_id");
			String access_token = userJson.optString("access_token");
			String username = userJson.optString("username");
			String fullname = userJson.optString("fullname");
			String email = userJson.optString("email");
			String ngaysinh = userJson.optString("bday");
			String sdt = userJson.optString("phone");
			String gioitinh = userJson.optString("gender");
			String sdt_lienhe = userJson.optString("phone_contact");
			String ghichu = userJson.optString("note");
			String avatar = userJson.optString("avatar");

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
			Global.savePreference(getApplicationContext(), Global.USER_AVATAR,
					avatar);
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
	@SuppressLint("InflateParams")
	private void ForgetPass() {
		LayoutInflater inflater = getLayoutInflater();
		View alertLayout = inflater.inflate(R.layout.dialog_active_register,
				null);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText txtActiveCode = (EditText) alertLayout
				.findViewById(R.id.edActiveCode);
		final TextView tvForget = (TextView) alertLayout.findViewById(R.id.tvCaption);
		tvForget.setText(getString(R.string.hintDialogForgetPass));
		txtActiveCode.setHint(R.string.hintDialogForget);
		alert.setView(alertLayout);
		alert.setCancelable(false);
		alert.setTitle("Quên Mật Khẩu");
		alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				return;

			}
		});

		alert.setPositiveButton("Gửi",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						SendForgetPass(txtActiveCode.getText().toString());
						dialog.dismiss();
						return;
					}
				});
		AlertDialog dialog = alert.create();
		dialog.show();
	}
	private void SendForgetPass(String txtemail) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("email", txtemail);
		client.post(Global.BASE_URI + "/" + Global.URI_FORGET_PASS, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
//						Log.e("loginToServer", response);
						NoticeRegisSuccsess(getString(R.string.success),getString(R.string.create_success));
						
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						NoticeRegisFalse(getString(R.string.ConfirmFalse),getString(R.string.create_false));
						switch (statusCode) {
						case 400:
							
							edtUsername.setError(getString(R.string.e401));
							edtUsername.requestFocus();
									
							break;
						case 403:
							edtUsername.setError(getString(R.string.e403));
							edtUsername.requestFocus();
							break;
						case 404:
							
							edtUsername.setError(getString(R.string.e404));
							edtUsername.requestFocus();
							break;
						case 503:
							edtUsername.setError(getString(R.string.e503));
							edtUsername.requestFocus();
							break;
						default:
							break;
						}
					}
				});
	}
	private void NoticeRegisSuccsess(String title, String content) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(content);

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.icon_tick);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// do something!
				onBackPressed();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
	private void NoticeRegisFalse(String title, String content) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(content);

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.icon_error);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// do something!
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
}
