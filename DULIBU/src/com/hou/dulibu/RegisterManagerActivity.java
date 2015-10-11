package com.hou.dulibu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hou.app.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterManagerActivity extends ActionBarActivity {
//	private RadioGroup radiosex;
//	private RadioButton rbButton;
//	private EditText edtEmail, edtUsername, edtPass, edtCk_pass, edtFullname, edtNgaysinh;
//	private Button sumit;
//	private CheckBox ck_dieukhoan;
//	private TextView dieukhoan;
//	private Context context = this;
//
//	private String email, username, pass, ck_pass, fullname, ngaysinh;
//	private int gioitinh;
//	
//	private Matcher matcher = null;
//	private Pattern pattern = null;
//	private String expression = null;
//	private CharSequence inputStr = null;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.register_manager);
//
//		
//		
//		if (getSupportActionBar() != null) {
//			getSupportActionBar().setDisplayShowCustomEnabled(true);
//			getSupportActionBar().setBackgroundDrawable(
//					new ColorDrawable(Color.parseColor("#0aae44")));
//			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		}
//		edtEmail = (EditText) findViewById(R.id.txtEmail);
//		edtUsername = (EditText) findViewById(R.id.txtUsername);
//		edtPass = (EditText) findViewById(R.id.txtPassword);
//		edtCk_pass = (EditText) findViewById(R.id.txtRePassword);
//		edtFullname = (EditText) findViewById(R.id.txtFullname);
//		edtNgaysinh = (EditText) findViewById(R.id.txtBirthday);
//		radiosex = (RadioGroup) findViewById(R.id.radiosex);
//		sumit = (Button) findViewById(R.id.btnRegister);
//		sumit.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (checkValidate()) {
//					registerToServer();
//				}
//			}
//		});
//		ck_dieukhoan = (CheckBox) findViewById(R.id.ck_dieukhoan);
//		int rbSelected = radiosex.getCheckedRadioButtonId();
//		rbButton = (RadioButton) findViewById(rbSelected);
//		dieukhoan = (TextView) findViewById(R.id.dieukhoan);
//		dieukhoan.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				showDialog();
//			}
//		});
//	}
//
//	private boolean checkValidate() {
//		boolean check = true;
//		email = edtEmail.getText().toString();
//		username = edtUsername.getText().toString();
//		pass = edtPass.getText().toString();
//		ck_pass = edtCk_pass.getText().toString();
//		fullname = edtFullname.getText().toString();
//		ngaysinh = edtNgaysinh.getText().toString();
//
//		email = "trunghieu1112@gmail.com";
//		username = "trunghieu";
////		pass = ck_pass
//		
//		expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
//	    inputStr = email;
//
//	    pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
//	    matcher = pattern.matcher(inputStr);
//		
//		int isselected = radiosex.getCheckedRadioButtonId();
//		if (isselected == R.id.rbmale) {
//			gioitinh = 1;
//		}else {
//			gioitinh = 2;
//		}
//		if (email.equalsIgnoreCase("") || username.equalsIgnoreCase("")
//				|| pass.equalsIgnoreCase("") || pass.equalsIgnoreCase(ck_pass)
//				|| fullname.equalsIgnoreCase("")
//				|| ngaysinh.equalsIgnoreCase("")) {
//			Toast.makeText(this, R.string.checknull + "", Toast.LENGTH_LONG).show();
//			check = false;
//		}else {
//			if (!matcher.matches()) {
//				check = false;
//			}else {
//				if (!ck_dieukhoan.isSelected()) {
//					check = false;
//				}
//			}
//		}
//
//		return check;
//	}
//
//	public void showDialog() {
//		final AlertDialog dialog = new AlertDialog.Builder(context).create();
//		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View view = inflater.inflate(R.layout.dieukhoan_dialog, null);
//		dialog.setView(view);
//		dialog.show();
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.register_manager, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == android.R.id.home) {
//			onBackPressed();
//		}
//		return super.onOptionsItemSelected(item);
//	}
//	
//	private void registerToServer() {
//		AsyncHttpClient client = new AsyncHttpClient();
//		RequestParams params = new RequestParams();
//		params.put("username", username);
//		params.put("password", pass);
//		params.put("password_cofirm", ck_pass);
//		params.put("email", email);
//		params.put("ngaysinh", Global.convertStringToDate(ngaysinh));
//		params.put("gioitinh", gioitinh);
//		params.put("is_agree_tos", "1");
//		client.post(Global.BASE_URI + "/" + Global.URI_DANGKY_PATH, params,
//				new AsyncHttpResponseHandler() {
//					public void onSuccess(String response) {
//						Log.e("loginToServer", response);
//						if (executeWhenRegisterSuccess(response)) {
//							Intent intent = new Intent(
//									RegisterManagerActivity.this,
//									ProfileManagerActivity.class);
//							startActivity(intent);
//						} else {
//							Toast.makeText(
//									getApplicationContext(),
//									getResources().getString(
//											R.string.login_again),
//									Toast.LENGTH_LONG).show();
//						}
//					}
//
//					@Override
//					public void onFailure(int statusCode, Throwable error,
//							String content) {
//						switch (statusCode) {
//						case 400:
//							Toast.makeText(getApplicationContext(), getResources().getString(R.string.e400),
//									Toast.LENGTH_LONG).show();
//							break;
//						case 403:
//							Toast.makeText(getApplicationContext(), getResources().getString(R.string.e403),
//									Toast.LENGTH_LONG).show();
//							break;
//						case 404:
//							Toast.makeText(getApplicationContext(), getResources().getString(R.string.e404),
//									Toast.LENGTH_LONG).show();
//							break;
//						case 503:
//							Toast.makeText(getApplicationContext(), getResources().getString(R.string.e503),
//									Toast.LENGTH_LONG).show();
//							break;
//						default:
//							break;
//						}
//					}
//				});
//	}
//	
//	private boolean executeWhenRegisterSuccess(String reponse) {
//		boolean check = true;
//		return check;
//	}
	
}
