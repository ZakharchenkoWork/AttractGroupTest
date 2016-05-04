package com.znshadows.attractgrouptest;

/**
 * Created by MisterY on 17.09.2015.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.net.URL;

public class PictureLoader extends AsyncTask<Void, Void, String> {

    private OnBitmapLoadListener onBitmapLoadListener = new OnBitmapLoadListener() {
        @Override
        public void onFinish(Bitmap bitmap) {
            Log.e("OnBitmapLoadListener","Custom listener is not defined");
        }
    };
    private String resultJson = "";
    private String dataSource = "";
    Bitmap downloadedPickture;
    public PictureLoader(String source, OnBitmapLoadListener onBitmapLoadListener)
    {
        dataSource = source;
        this.onBitmapLoadListener = onBitmapLoadListener;
    }

    @Override
    protected String doInBackground(Void... params) {
        // получаем данные с внешнего ресурса


        try {
            URL newurl = new URL(dataSource);
            //loading picture
            downloadedPickture = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            //calls listener when finished


        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJson;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.e("Loading", "listener called, PictureLoader" );
        onBitmapLoadListener.onFinish(downloadedPickture);

    }

    public interface OnBitmapLoadListener {
        public void onFinish(Bitmap bitmap);
    }
}