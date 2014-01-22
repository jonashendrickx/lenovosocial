package com.lenovo.social;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bitmapImage;

    public BitmapDownloaderTask(ImageView bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
    	bitmapImage.setImageBitmap(result);
    }
}
