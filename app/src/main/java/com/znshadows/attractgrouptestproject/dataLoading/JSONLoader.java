package com.znshadows.attractgrouptestproject.dataLoading;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kostya on 04.05.2016.
 */
public abstract class JSONLoader extends AsyncTask<Void, Void, String> {

    private final String dataSource;


    //Listener to be called after all data is loaded
    private OnLoadFinishListener onLoadFinishListener = new OnLoadFinishListener() {
        @Override
        public void onFinish() {
            Log.w("OnLoadFinishListener", "JSONLoader: Custom listener is not defined");
        }
    };


    /**
     * @param dataURL              link to the JSON file.
     * @param onLoadFinishListener implement this interface to get results in place
     *                             where you create object of this class
     */
    public JSONLoader(String dataURL, OnLoadFinishListener onLoadFinishListener) {
        this.onLoadFinishListener = onLoadFinishListener;
        this.dataSource = dataURL;
    }

    @Override
    protected String doInBackground(Void... params) {
        String resultJson = "";
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            //Preparing URL
            URL url = new URL(dataSource);

            //connecting to Server
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //Preparing to read data
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            //reading JSON
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            resultJson = buffer.toString();
            urlConnection.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultJson;
    }

    @Override
    protected void onPostExecute(String strJson) {
        super.onPostExecute(strJson);
        //call for Owerided method in ancestor
        parcing(strJson);

    }

    public OnLoadFinishListener getOnLoadFinishListener() {
        return onLoadFinishListener;
    }

    /**
     * Owerride this method in ancestor and parce String obtained from server
     *
     * @param jsonString string with JSON code from server
     */
    abstract protected void parcing(String jsonString);


    public interface OnLoadFinishListener {
        public void onFinish();
    }

}
