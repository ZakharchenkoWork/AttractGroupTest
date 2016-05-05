package com.znshadows.attractgrouptest;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
/**
 * Created by kostya on 04.05.2016.
 */
abstract class JSONLoader extends AsyncTask<Void, Void, String> {
    public OnLoadFinishListener getOnLoadFinishListener() {
        return onLoadFinishListener;
    }

    private OnLoadFinishListener onLoadFinishListener = new OnLoadFinishListener() {
        @Override
        public void onFinish() {
            Log.e("OnLoadFinishListener","JSONLoader: Custom listener is not defined");
        }
    };


        private final String dataSource;


    public JSONLoader(String dataSource, OnLoadFinishListener onLoadFinishListener) {
        this.onLoadFinishListener = onLoadFinishListener;
        this.dataSource = dataSource;
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
            parcing(strJson);

        }
    abstract protected void parcing(String jsonString);
    public interface OnLoadFinishListener {
        public void onFinish();
    }

}
