package com.hou.dulibu;

import it.neokree.materialnavigationdrawer.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.DTDHandler;

import com.hou.app.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.R.bool;
import android.annotation.SuppressLint;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import android.widget.Toast;

public class RegisterManagerActivity extends ActionBarActivity {

	private RadioGroup radiosex;
	private EditText edtEmail, edtUsername, edtPass, edtCk_pass, edtFullname;
	private Button sumit;
	private CheckBox ck_dieukhoan;
	private TextView dieukhoan;
	private static EditText edtNgaysinh;
	private Context context = this;
	private String email, username, pass, ck_pass, fullname;
	private static String ngaysinh = "";
	private int gioitinh = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_manager);

		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayShowCustomEnabled(true);
			getSupportActionBar().setBackgroundDrawable(
					new ColorDrawable(Color.parseColor("#0aae44")));
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}

		edtEmail = (EditText) findViewById(R.id.txtEmail);
		edtUsername = (EditText) findViewById(R.id.txtUsername);
		edtPass = (EditText) findViewById(R.id.txtPassword);
		edtCk_pass = (EditText) findViewById(R.id.txtRePassword);
		edtFullname = (EditText) findViewById(R.id.txtFullname);
		edtNgaysinh = (EditText) findViewById(R.id.txtBirthday);
		radiosex = (RadioGroup) findViewById(R.id.radiosex);

		sumit = (Button) findViewById(R.id.btnSubmit);
		sumit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (checkValidate()) {
					registerToServer();
				Intent intent = new Intent(RegisterManagerActivity.this,
						LoginManagerActivity.class);
				startActivity(intent);
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
				// datePikerDialog(R.id.dpCreateDatePicker,
				// R.id.btnDoneCreateTripDatePiker,
				// R.id.btnCancelCreateTripDatePiker, edtNgaysinh,
				// R.layout.date_picker, R.string.titleTimeDialog);
				showDatePickerDialog(v);
			}
		});
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
					Locale.US);
			try {
				ngaysinh = dateFormat
						.format(Global.getDateFromDatePicket(view));
			} catch (Exception e) {
				// TODO: handle exception
			}
			edtNgaysinh.setText(ngaysinh);

		}
	}

	@SuppressLint("DefaultLocale") @SuppressWarnings("deprecation")
	private boolean checkValidate() {
		boolean check = true;
		email = edtEmail.getText().toString().toLowerCase();
		username = edtUsername.getText().toString().toLowerCase();
		pass = edtPass.getText().toString();
		ck_pass = edtCk_pass.getText().toString();
		fullname = edtFullname.getText().toString();

		int isselected = radiosex.getCheckedRadioButtonId();
		if (isselected == R.id.rbmale) {
			gioitinh = 1;
		} else {
			gioitinh = 2;
		}
		int tuoi = 0;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.US);
		try {
			Date date = dateFormat.parse(ngaysinh);
			tuoi = (int) (c.get(Calendar.YEAR) - date.getYear());
			if (c.get(Calendar.MONTH) == date.getMonth()) {
				if (c.get(Calendar.DATE) >= date.getDate()) {
					tuoi = tuoi - 1900;
				} else {
					tuoi = tuoi - 1901;
				}
			} else {
				if (c.get(Calendar.MONTH) > date.getMonth()) {
					tuoi = tuoi - 1900;
				} else {
					tuoi = tuoi - 1901;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (CheckNullInfo()) {
			check = false;
		} else {
			if (!Global.isValidEmail(email)) {
				edtEmail.setError(getString(R.string.register_err_email));
				edtEmail.requestFocus();
				check = false;
			} else {
				if (pass.equals(ck_pass) == false) {
					edtCk_pass.setError(getString(R.string.ck_pass_err));
					edtCk_pass.requestFocus();
					edtPass.setText("");
					edtCk_pass.setText("");
					check = false;
				} else {
					if (pass.length() <= 4) {
						edtPass.setError(getString(R.string.pass_err));
						edtPass.requestFocus();
						check = false;
					} else {
						if (ck_dieukhoan.isChecked() != true) {
							ck_dieukhoan
									.setError(getString(R.string.ck_rule_err));
							ck_dieukhoan.requestFocus();
							check = false;
						} else {
							if (tuoi <= 15) {
								edtNgaysinh
										.setError(getString(R.string.error_tuoi));
								edtNgaysinh.requestFocus();
								check = false;
							}
						}

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
		params.put("gender", gioitinh + "");
		params.put("is_agree_tos", "1");

		client.post(Global.BASE_URI + "/" + Global.URI_DANGKY_PATH, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("registerToServer", response);
						ConfirmRegister(response);
						/*
						 * Intent intent = new
						 * Intent(RegisterManagerActivity.this,
						 * LoginManagerActivity.class); startActivity(intent);
						 */

						/*
						 * if (executeWhenRegisterSuccess(response)) {
						 * Toast.makeText(getApplicationContext(),
						 * getResources().getString(R.string.register_succes),
						 * Toast.LENGTH_SHORT).show(); // Intent intent = new
						 * Intent( // RegisterManagerActivity.this, //
						 * LoginManagerActivity.class); startActivity(intent); }
						 * else { Toast.makeText(getApplicationContext(),
						 * getResources().getString(R.string.register_error),
						 * Toast.LENGTH_LONG).show(); }
						 */
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						switch (statusCode) {
						case 400:
							Toast.makeText(getApplicationContext(),
									getResources().getString(R.string.e400),
									Toast.LENGTH_LONG).show();
							Log.e("ERROR", content);

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
							Toast.makeText(
									getApplicationContext(),
									getResources().getString(
											R.string.register_error),
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

	// public void ActiveDialog(final String respond) {
	// final Dialog dialog = new Dialog(this);
	// dialog.setContentView(R.layout.dialog_active_register);
	// // dialog.setTitle(dialogTitle);
	// dialog.setCancelable(true);
	// final EditText tvActiveCode = (EditText) dialog
	// .findViewById(R.id.edActiveCode);
	// Button btnCancelActiver = (Button) dialog
	// .findViewById(R.id.btnCancelAcvtiveRegister);
	// btnCancelActiver.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// Intent intent = new Intent(RegisterManagerActivity.this,
	// LoginManagerActivity.class);
	// startActivity(intent);
	// }
	// });
	// Button btnActive = (Button) dialog
	// .findViewById(R.id.btnAcvtiveRegister);
	// btnActive.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// // TextView edTimePlace = (TextView) findViewById(textID);
	// if (tvActiveCode.getText().toString().equalsIgnoreCase(respond)) {
	// Toast.makeText(RegisterManagerActivity.this,
	// getString(R.string.DoneActive), Toast.LENGTH_SHORT)
	// .show();
	// Intent intent = new Intent(RegisterManagerActivity.this,
	// LoginManagerActivity.class);
	// startActivity(intent);
	// } else {
	// tvActiveCode.setText("");
	// Toast.makeText(RegisterManagerActivity.this,
	// getString(R.string.FailActive), Toast.LENGTH_SHORT)
	// .show();
	// }
	// }
	// });
	// // Show Dialog
	// dialog.show();
	// }

	@SuppressLint("InflateParams")
	private void ConfirmRegister(final String respond) {
		LayoutInflater inflater = getLayoutInflater();
		View alertLayout = inflater.inflate(R.layout.dialog_active_register,
				null);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText tvActiveCode = (EditText) alertLayout
				.findViewById(R.id.edActiveCode);
		alert.setView(alertLayout);
		alert.setCancelable(false);
		alert.setTitle("Xác Nhận Email Đăng Ký");
		alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(RegisterManagerActivity.this,
						LoginManagerActivity.class);
				startActivity(intent);
				dialog.dismiss();
				return;

			}
		});

		alert.setPositiveButton("Xác nhận",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (tvActiveCode.getText().toString()
								.equalsIgnoreCase(respond)) {
							Toast.makeText(RegisterManagerActivity.this,
									getString(R.string.DoneActive),
									Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(
									RegisterManagerActivity.this,
									LoginManagerActivity.class);
							startActivity(intent);
						} else {
							tvActiveCode.setText("");
							Toast.makeText(RegisterManagerActivity.this,
									getString(R.string.FailActive),
									Toast.LENGTH_SHORT).show();
						}
						return;
					}
				});
		AlertDialog dialog = alert.create();
		dialog.show();
	}

	private boolean executeWhenRegisterSuccess(String reponse) {
		boolean check = true;
		return check;
	}
	private boolean CheckNullInfo(){
		
		if (edtEmail.getText().toString().trim().equals("")) {
			edtEmail.setError(getString(R.string.checknull));
			edtEmail.requestFocus();
			return true;
		}else {
			if (edtUsername.getText().toString().trim().equals("")) {
				edtUsername.setError(getString(R.string.checknull));
				edtUsername.requestFocus();
				return true;
			}else {
				if (edtPass.getText().toString().trim().equals("")) {
					edtPass.setError(getString(R.string.checknull));
					edtPass.requestFocus();
					return true;
				}else {
					if (edtFullname.getText().toString().trim().equals("")) {
						edtFullname.setError(getString(R.string.checknull));
						edtFullname.requestFocus();
						return true;
					}
				}
			}
		}
		return false;
	}

}
