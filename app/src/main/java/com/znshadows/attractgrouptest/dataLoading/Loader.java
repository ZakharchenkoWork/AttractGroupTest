package com.znshadows.attractgrouptest.dataLoading;

import android.graphics.Bitmap;
import android.util.Log;

import com.znshadows.attractgrouptest.data.SuperHero;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kostya on 05.05.2016.
 */
public class Loader extends JSONLoader {
    public Loader(String dataSource, OnLoadFinishListener onLoadFinishListener) {
        super(dataSource, onLoadFinishListener);
    }

    @Override
    protected void parcing(String jsonString) {
        JSONArray mainJsonArray = null;

        try {

            mainJsonArray = new JSONArray(jsonString);

            Log.d("JSON", ""+jsonString);
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
                        Log.e("Loading", "listener called, in JSONLoader, downloaded: ");
                        superHero.setImage(bitmap);
                        SuperHero.addHeroToList(superHero);
                        getOnLoadFinishListener().onFinish();


                    }
                });
                loadPicture.execute();


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
