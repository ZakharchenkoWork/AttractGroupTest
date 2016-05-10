package com.znshadows.attractgrouptestproject.dataLoading;

import android.graphics.Bitmap;
import android.util.Log;

import com.znshadows.attractgrouptestproject.data.SuperHero;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kostya on 05.05.2016.
 */
public class Loader extends JSONLoader {

    /**
     * @param dataURL              link to the JSON file.
     * @param onLoadFinishListener implement this interface to get results in place
     *                             where you create object of this class
     */

    public Loader(String dataURL, OnLoadFinishListener onLoadFinishListener) {
        super(dataURL, onLoadFinishListener);
    }

    /**
     * @param jsonString string with JSON code from server
     */
    @Override
    protected void parcing(String jsonString) {
        try {
            //all data from JSON file to Array
            JSONArray mainJsonArray = new JSONArray(jsonString);

            Log.d("JSON", "" + jsonString);
            //Parcing of every object one by one
            for (int i = 0; i < mainJsonArray.length(); i++) {

                JSONObject superHeroJSON = mainJsonArray.getJSONObject(i);
                //filling up object with obtained data
                final SuperHero superHero = new SuperHero(superHeroJSON.getInt("itemId"),
                        superHeroJSON.getString("name"),
                        superHeroJSON.getString("description"), superHeroJSON.getLong("time"));


                //Loading Picture
                PictureLoader loadPicture = new PictureLoader(superHeroJSON.getString("image"), //passing image URL
                        // called when picture download finished
                        new PictureLoader.OnBitmapLoadListener() {
                            @Override
                            public void onFinish(Bitmap bitmap) {
                                Log.d("Loading", "listener called, in JSONLoader, downloaded: ");
                                superHero.setImage(bitmap);

                                SuperHero.addHeroToList(superHero);

                                //notify creator that object is fully downloaded
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
