package com.hou.asynctask;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hou.dulibu.SplashScreenActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class GetListCityAsyncTask extends AsyncTask<String, Void, Void> {
    
   private final HttpClient Client = new DefaultHttpClient();
   private String Content;
   private String Error = null;
   //private ProgressDialog Dialog = ProgressDialog();
    
   //TextView uiUpdate = (TextView) findViewById(R.id.output);
   
   private String listCity(String response) {
   	String ketqua;
		try {
			JSONArray arrObj = new JSONArray(response);
			for (int i = 0; i < arrObj.length(); i++) {
				JSONObject cityLocationJson = arrObj.getJSONObject(i);
				//Tinh_Thanhpho city = new Tinh_Thanhpho();

				String _id = cityLocationJson.optString("_id");
				String ghichu = cityLocationJson.optString("note");
				String lat = cityLocationJson.optString("lat") + "";
				String lon = cityLocationJson.optString("lon") + "";
				String tenTinh = cityLocationJson.optString("name");
				Log.e("listCity", tenTinh);
				String image = cityLocationJson.optString("image");
				/*city.setMaTinh(_id);
				city.setGhichu(ghichu);
				city.setLat(lat);
				city.setLon(lon);
				city.setImage(image);
				city.setTenTinh(tenTinh);*/
			}

			// Toast.makeText(getApplicationContext(), "KQ JSON",
			// Toast.LENGTH_LONG).show();
			
			return "true";
		} catch (JSONException e) {
			e.printStackTrace();
			return "false";
		}

	}
   
   protected void onPreExecute() {
       // NOTE: You can call UI Element here.
        
       //UI Element
       /*uiUpdate.setText("Output : ");
       Dialog.setMessage("Downloading source..");
       Dialog.show();*/
   }

   // Call after onPreExecute method
   protected Void doInBackground(String... urls) {
       try {
            
           // Call long running operations here (perform background computation)
           // NOTE: Don't call UI Element here.
            
           // Server url call by GET method
           HttpGet httpget = new HttpGet(urls[0]);
           ResponseHandler<String> responseHandler = new BasicResponseHandler();
           Content = Client.execute(httpget, responseHandler);
           Content = listCity(Content);
            
       } catch (ClientProtocolException e) {
           Error = e.getMessage();
           cancel(true);
       } catch (IOException e) {
           Error = e.getMessage();
           cancel(true);
       }
        
       return null;
   }
    
   protected void onPostExecute(Void unused) {
       // NOTE: You can call UI Element here.
        
       // Close progress dialog
       //Dialog.dismiss();
        
       if (Error != null) {
            
           //uiUpdate.setText("Output : "+Error);
    	   Log.e("ketQua", Error);
            
       } else {
            
           //uiUpdate.setText("Output : "+Content);
    	   Log.e("KetQua", Content);
            
        }
   }
    
}
