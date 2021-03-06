/*
 * @author Maver1ck
 * */

package com.hou.upload;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/*
 * here we are going to use an AsyncTask to download the image 
 *      in background while showing the download progress
 * */

public class ImageDownloader extends AsyncTask<Void, Integer, Void> {

	private String url;
	private Context c;
	private ImageView img;
	private Bitmap bmp;
	private ImageLoaderListener listener;

	/*--- constructor ---*/
	public ImageDownloader(String url, ImageView img, Context c, Bitmap bmp,
			ImageLoaderListener listener) {
		/*--- we need to pass some objects we are going to work with ---*/
		this.url = url;
		this.c = c;
		this.img = img;
		this.bmp = bmp;
		this.listener = listener;
	}

	public interface ImageLoaderListener {

		void onImageDownloaded(Bitmap bmp);

	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... arg0) {

		bmp = getBitmapFromURL(url);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (listener != null) {
			listener.onImageDownloaded(bmp);
			}
		img.setImageBitmap(bmp);
		super.onPostExecute(result);
	}

	public static Bitmap getBitmapFromURL(String link) {
		/*--- this method downloads an Image from the given URL, 
		 *  then decodes and returns a Bitmap object
		 ---*/
		try {
			URL url = new URL(link);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);

			return myBitmap;

		} catch (IOException e) {
			e.printStackTrace();
			Log.e("getBmpFromUrl error: ", e.getMessage().toString());
			return null;
		}
	}

}
