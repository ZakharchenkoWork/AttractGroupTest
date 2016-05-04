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
public class JSONLoader extends AsyncTask<Void, Void, String> {
    private OnLoadFinishListener onLoadFinishListener = new OnLoadFinishListener() {
        @Override
        public void onFinish(ArrayList<SuperHero> heroes) {
            Log.e("OnLoadFinishListener","JSONLoader: Custom listener is not defined");
        }
    };
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        private String resultJson = "";
        private final String dataSource;


    public JSONLoader(String dataSource, OnLoadFinishListener onLoadFinishListener) {
        this.onLoadFinishListener = onLoadFinishListener;
        this.dataSource = dataSource;
    }

    @Override
        protected String doInBackground(Void... params) {

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

            JSONArray mainJsonArray = null;
            String secondName = "";

            try {

                mainJsonArray = new JSONArray(strJson);
                final ArrayList<SuperHero> heroes = new ArrayList<SuperHero>();
                Log.d("JSON", ""+strJson);
                for(int i = 0; i < mainJsonArray.length(); i++)
                {
                    JSONObject superHeroJSON = mainJsonArray.getJSONObject(i);
                    final SuperHero superHero = new SuperHero(superHeroJSON.getInt("itemId"),
                            superHeroJSON.getString("name"),
                            superHeroJSON.getString("description"), superHeroJSON.getLong("time"));


                    //Loading Picture
                    PictureLoader loadPicture = new PictureLoader(superHeroJSON.getString("image"), new PictureLoader.OnBitmapLoadListener() {
                        @Override
                        public void onFinish(Bitmap bitmap) {
                            superHero.setImage(bitmap);
                            heroes.add(superHero);
                        }
                    });
                    loadPicture.execute();


                }
                onLoadFinishListener.onFinish(heroes);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    public interface OnLoadFinishListener {
        public void onFinish(ArrayList<SuperHero> heroes);
    }

}
