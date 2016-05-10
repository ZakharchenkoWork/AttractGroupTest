package com.znshadows.attractgrouptestproject.dataLoading;

/**
 * Created by MisterY on 17.09.2015.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

public class PictureLoader extends AsyncTask<Void, Void, String> {
    //Listener to be called after all data is loaded
    private OnBitmapLoadListener onBitmapLoadListener = new OnBitmapLoadListener() {
        @Override
        public void onFinish(Bitmap bitmap) {
            Log.w("OnBitmapLoadListener", "Custom listener is not defined");
        }
    };

    private String resultJson = "";
    private String dataURL = "";
    private Bitmap downloadedPickture;

    /**
     * @param dataURL              link to the JSON file.
     * @param onBitmapLoadListener implement this interface to get picture in place
     *                             where you create object of this class
     */
    public PictureLoader(String dataURL, OnBitmapLoadListener onBitmapLoadListener) {
        this.dataURL = dataURL;
        this.onBitmapLoadListener = onBitmapLoadListener;
    }

    @Override
    protected String doInBackground(Void... params) {


        try {
            //Recieve data from server
            URL newurl = new URL(dataURL);

            //loading picture
            downloadedPickture = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());



        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJson;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("Loading", "listener called, PictureLoader");
        //calls listener when finished
        onBitmapLoadListener.onFinish(downloadedPickture);

    }

    public interface OnBitmapLoadListener {
        public void onFinish(Bitmap bitmap);
    }
}