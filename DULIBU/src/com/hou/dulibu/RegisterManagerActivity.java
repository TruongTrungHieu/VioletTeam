package com.hou.dulibu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
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
	private RadioGroup radiosex;
	private RadioButton rbButton;
	private EditText email,username,pass,ck_pass,fullname,ngaysinh;
	private Button sumit;
	private CheckBox ck_dieukhoan;
	private TextView dieukhoan;
	private Context context=this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_manager);
		
		if(getSupportActionBar() != null){
			getSupportActionBar().setDisplayShowCustomEnabled(true);
			getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0aae44")));
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		email = (EditText) findViewById(R.id.txtEmail);
		username = (EditText) findViewById(R.id.txtUsername);
		pass = (EditText) findViewById(R.id.txtPassword);
		ck_pass = (EditText) findViewById(R.id.txtRePassword);
		fullname = (EditText) findViewById(R.id.txtFullname);
		ngaysinh = (EditText) findViewById(R.id.txtBirthday);
		radiosex = (RadioGroup) findViewById(R.id.radiosex);
		sumit = (Button) findViewById(R.id.btnRegister);
		sumit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			CheckboxSelected();	
			}
		});
		ck_dieukhoan = (CheckBox) findViewById(R.id.ck_dieukhoan);
		int rbSelected = radiosex.getCheckedRadioButtonId();
		rbButton = (RadioButton) findViewById(rbSelected);
		dieukhoan = (TextView) findViewById(R.id.dieukhoan);
		dieukhoan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog();
			}
		});
	}
	public void CheckboxSelected(){
		if(email.getText().toString().equals("")||username.getText().toString().equals("")||pass.getText().toString().equals("")
				|| ck_pass.getText().toString().equals("")||fullname.getText().toString().equals("")||ngaysinh.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(),"Nháº­p thÃ´ng tin chÆ°a Ä‘áº§y Ä‘á»§",Toast.LENGTH_SHORT).show();
		}
		else
		{
     		 if (email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && email.getText().length() > 0)
				        { 
				        	if(pass.getText().toString().equals(ck_pass.getText().toString())){
								if(ck_dieukhoan.isChecked()){
									sumit.setEnabled(false);
									Toast.makeText(getApplication(), "a", Toast.LENGTH_SHORT).show();
								}
								else{
									sumit.setEnabled(true);
									Toast.makeText(getApplicationContext(),"ChÆ°a xÃ¡c nháº­n Ä‘iá»�u khoáº£n",Toast.LENGTH_SHORT).show();
							    }
							}
							else{
								Toast.makeText(getApplicationContext(),"XÃ¡c nháº­n pass khÃ´ng trÃ¹ng",Toast.LENGTH_SHORT).show();
								pass.setText("");
								ck_pass.setText("");
							}
				        }
				        else
				        {
				             Toast.makeText(getApplicationContext(),"Nháº­p mail sai Ä‘á»‹nh dáº¡ng",Toast.LENGTH_SHORT).show();
				             email.setText("");
				        }
				}
	}
	
	 public void showDialog(){
         final AlertDialog dialog=new AlertDialog.Builder(context).create();
        LayoutInflater inflater=(LayoutInflater)
                                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.dieukhoan_dialog, null);
       
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
}
