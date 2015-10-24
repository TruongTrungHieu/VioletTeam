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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Change_Password_Activity extends ActionBarActivity {
	private Button changepass;
	private EditText edt_oldpass,edt_newpass,edt_check_newpass;
	private String oldpass,newpass,check_newpass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.change_password);
		changepass = (Button) findViewById(R.id.btSavePassChange);
		edt_oldpass = (EditText) findViewById(R.id.edt_old_pass);
		edt_newpass = (EditText) findViewById(R.id.edt_new_pass);
		edt_check_newpass = (EditText) findViewById(R.id.edt_check_new_pass);
		changepass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkValidate()) {
					changePass();
//					Intent intent = new Intent(Change_Password_Activity.this,
//							SettingActivity.class);
//					startActivity(intent);
//					Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public boolean checkValidate(){
		boolean check = true;
		oldpass = edt_oldpass.getText().toString();
		newpass = edt_newpass.getText().toString();
		check_newpass = edt_check_newpass.getText().toString();
		if (CheckNullInfo()) {
			check = false;
		}else{
			if(newpass.length()<4){
				edt_newpass.setError(getString(R.string.pass_err));
				edt_check_newpass.requestFocus();
				edt_newpass.setText("");
				edt_check_newpass.setText("");
				check = false;
			}else{
			if (newpass.equals(check_newpass) == false) {
				edt_newpass.setError(getString(R.string.ck_pass_err));
				edt_check_newpass.requestFocus();
				edt_newpass.setText("");
				edt_check_newpass.setText("");
				check = false;
		      }
			}
		}
		return check;
	}
     
	private boolean CheckNullInfo(){
		
		if (edt_oldpass.getText().toString().trim().equals("")) {
			edt_oldpass.setError(getString(R.string.checknull));
			edt_oldpass.requestFocus();
			return true;
		}else {
			if (edt_newpass.getText().toString().trim().equals("")) {
				edt_newpass.setError(getString(R.string.checknull));
				edt_newpass.requestFocus();
				return true;
			}else {
				if (edt_check_newpass.getText().toString().trim().equals("")) {
					edt_check_newpass.setError(getString(R.string.checknull));
					edt_check_newpass.requestFocus();
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	private void changePass() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("old_password", edt_oldpass.getText().toString());
		params.put("new_password", edt_newpass.getText().toString());
		client.post(Global.BASE_URI + "/" + Global.URI_CHANGE_PASS_PATH+"?access_token="+ Global.getPreference(this, Global.USER_ACCESS_TOKEN, "")
				, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("cc", response);
							Intent intent = new Intent(Change_Password_Activity.this, SettingActivity.class);
							startActivity(intent);
						Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
					   
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						Log.e("aaa", content);
						edt_oldpass.setText(null);
						edt_newpass.setText(null);
						edt_check_newpass.setText(null);
						switch (statusCode) {
						case 400:
							Toast.makeText(getApplicationContext(),
									getResources().getString(R.string.e402),
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
