package com.hou.dulibu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hou.app.Global;
import com.hou.model.Tinh_Thanhpho;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class RegisterManagerActivity extends ActionBarActivity {

	private RadioGroup radiosex;
	private EditText edtEmail, edtUsername, edtPass, edtCk_pass, edtFullname;
	private Button sumit;
	private CheckBox ck_dieukhoan;
	private TextView dieukhoan, edtNgaysinh;
	private Context context = this;

	private String email, username, pass, ck_pass, fullname, ngaysinh;
	private int gioitinh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_manager);

		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayShowCustomEnabled(true);
			getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0aae44")));
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}

		edtEmail = (EditText) findViewById(R.id.txtEmail);
		edtUsername = (EditText) findViewById(R.id.txtUsername);
		edtPass = (EditText) findViewById(R.id.txtPassword);
		edtCk_pass = (EditText) findViewById(R.id.txtRePassword);
		edtFullname = (EditText) findViewById(R.id.txtFullname);
		edtNgaysinh = (TextView) findViewById(R.id.txtBirthday);
		radiosex = (RadioGroup) findViewById(R.id.radiosex);

		sumit = (Button) findViewById(R.id.btnSubmit);
		sumit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(true){
				registerToServer();
				}
			}
		});
		ck_dieukhoan = (CheckBox) findViewById(R.id.ck_dieukhoan);

		dieukhoan = (TextView) findViewById(R.id.dieukhoan);
		dieukhoan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog();
			}
		});
		edtNgaysinh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datePikerDialog(R.id.dpCreateDatePicker, R.id.btnDoneCreateTripDatePiker,
						R.id.btnCancelCreateTripDatePiker, edtNgaysinh, R.layout.date_picker, R.string.titleTimeDialog);
			}
		});
	}

	public void datePikerDialog(int datePickerID, int btnDoneID, int btnCancelID, final TextView tv, int Layout,
			int dialogTitle) {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(Layout);
		dialog.setTitle(dialogTitle);
		dialog.setCancelable(true);
		final DatePicker dpDatePK = (DatePicker) dialog.findViewById(datePickerID);

		Button btnCancelDatePiker = (Button) dialog.findViewById(btnCancelID);
		btnCancelDatePiker.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		Button btnDoneDatePicker = (Button) dialog.findViewById(btnDoneID);
		btnDoneDatePicker.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub\
				int month = dpDatePK.getMonth() + 1;
				tv.setText(dpDatePK.getYear() + "-" + month + "-" + dpDatePK.getDayOfMonth());
				dialog.dismiss();
			}
		});
		// Show Dialog
		dialog.show();
	}

	private boolean checkValidate() {
		boolean check = true;

		int isselected = radiosex.getCheckedRadioButtonId();
		if (isselected == R.id.rbmale) {
			gioitinh = 1;
		} else {
			gioitinh = 2;
		}

		if (email.equals("")== true || username.equals("")== true || pass.equals("")== true
				|| ck_pass.equals("")== true|| fullname.equals("")== true
				|| ngaysinh.equals("")== true) {
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.checknull), Toast.LENGTH_LONG).show();
			check = false;
		} else {
			if (!Global.isValidEmail(email)) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.register_err_email),
						Toast.LENGTH_SHORT).show();
				edtEmail.setText("");
				check = false;
			} else {
				if(pass.equals(ck_pass)==false){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.ck_pass_err),
							Toast.LENGTH_SHORT).show();
					edtPass.setText("");
					edtCk_pass.setText("");
					check = false;
				}else{
					if (ck_dieukhoan.isChecked()==true) {
					}else{
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.ck_rule_err),
								Toast.LENGTH_SHORT).show();
						check = false;
					}
				}
				
			}
		}
		return check;
	}

	@SuppressLint("InflateParams")
	public void showDialog() {
		final AlertDialog dialog = new AlertDialog.Builder(context).create();
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dieukhoan_dialog, null);
		dialog.setView(view);
		dialog.show();
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

	private void registerToServer() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		params.put("username", username);
		params.put("password", pass);
		params.put("password_confirm", ck_pass);
		params.put("email", email);
		params.put("fullname", fullname);
		params.put("bday", ngaysinh);
		params.put("gender", gioitinh);
		params.put("is_agree_tos", "1");

		client.post(Global.BASE_URI + "/" + Global.URI_DANGKY_PATH, params, new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
				Log.e("registerToServer", response);
				ActiveDialog(idRespone(response));
				/*
				 * Intent intent = new Intent(RegisterManagerActivity.this,
				 * LoginManagerActivity.class); startActivity(intent);
				 */

				/*
				 * if (executeWhenRegisterSuccess(response)) {
				 * Toast.makeText(getApplicationContext(),
				 * getResources().getString(R.string.register_succes),
				 * Toast.LENGTH_SHORT).show(); // Intent intent = new Intent( //
				 * RegisterManagerActivity.this, // LoginManagerActivity.class);
				 * startActivity(intent); } else {
				 * Toast.makeText(getApplicationContext(),
				 * getResources().getString(R.string.register_error),
				 * Toast.LENGTH_LONG).show(); }
				 */
			}

			@Override
			public void onFailure(int statusCode, Throwable error, String content) {
				switch (statusCode) {
				case 400:
//					Toast.makeText(getApplicationContext(), getResources().getString(R.string.e400), Toast.LENGTH_LONG)
//							.show();
					Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG)
					.show();
			
					break;
				case 403:
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.e403), Toast.LENGTH_LONG)
							.show();
					break;
				case 404:
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.e404), Toast.LENGTH_LONG)
							.show();
					break;
				case 503:
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.e503), Toast.LENGTH_LONG)
							.show();
					break;
				default:
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.register_error),
							Toast.LENGTH_LONG).show();
					break;
				}
			}
		});
	}

	private String idRespone(String response) {
		String id = null;
		try {
			JSONArray arrObj = new JSONArray(response);
			for (int i = 0; i < arrObj.length(); i++) {
				JSONObject cityLocationJson = arrObj.getJSONObject(i);
				id = cityLocationJson.optString("_id");
				Log.e("idRespone", id);
			}
			return id;
		} catch (JSONException e) {
			e.printStackTrace();
			return "false";
		}

	}

	public void ActiveDialog(final String respond) {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_active_register);
		// dialog.setTitle(dialogTitle);
		dialog.setCancelable(true);
		final EditText tvActiveCode = (EditText) dialog.findViewById(R.id.edActiveCode);
		Button btnCancelActiver = (Button) dialog.findViewById(R.id.btnCancelAcvtiveRegister);
		btnCancelActiver.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisterManagerActivity.this, LoginManagerActivity.class);
				startActivity(intent);
			}
		});
		Button btnActive = (Button) dialog.findViewById(R.id.btnAcvtiveRegister);
		btnActive.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// TextView edTimePlace = (TextView) findViewById(textID);
				if (tvActiveCode.getText().toString().equalsIgnoreCase(respond)) {
					Toast.makeText(RegisterManagerActivity.this, getString(R.string.DoneActive), Toast.LENGTH_SHORT)
							.show();
					Intent intent = new Intent(RegisterManagerActivity.this, LoginManagerActivity.class);
					startActivity(intent);
				} else {
					tvActiveCode.setText("");
					Toast.makeText(RegisterManagerActivity.this, getString(R.string.FailActive), Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		// Show Dialog
		dialog.show();
	}

	private boolean executeWhenRegisterSuccess(String reponse) {
		boolean check = true;
		return check;
	}

}
