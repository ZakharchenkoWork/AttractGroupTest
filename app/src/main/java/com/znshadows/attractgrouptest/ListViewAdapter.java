package com.znshadows.attractgrouptest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znshadows.attractgrouptest.data.SuperHero;

/**
 * Created by kostya on 04.05.2016.
 */
public class ListViewAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater lInflater;
    private boolean isFilterSet = false;
    private String filterText;

    public ListViewAdapter(Context context) {

        ctx = context;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * also helps to show filtered result
     *
     * @return total amount of items
     */
    @Override
    public int getCount() {
        if (isFilterSet) {
            if (isItemFilteredAvailiable()) { // show 1 result
                return 1;
            } else { // Show nothing
                return 0;
            }
        } else {// show all items
            return SuperHero.getAllHeroes().size();
        }
    }

    /**
     * also helps to show filtered result
     *
     * @return Object(item) which shold be used in this iteration
     */
    @Override
    public Object getItem(int position) {
        if (isFilterSet) { //if filter is on
            if (isItemFilteredAvailiable()) {//and we found smth
                return getFilteredHero(); //founded item
            }
        }
        //normal items one by one to list
        return SuperHero.getAllHeroes().get(position);
    }

    /**
     * also helps to show filtered result
     */
    @Override
    public long getItemId(int position) {
        if (isFilterSet) {//if filter is on
            if (isItemFilteredAvailiable()) {//and we found smth
                return getFilteredHeroId();//id of founded item
            }
        }
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        //inserting empty view
        if (view == null) {
            view = lInflater.inflate(R.layout.main_list_item, parent, false);
        }
        // for correct displaying of data
        if (isFilterSet) {
            if (isItemFilteredAvailiable()) {
                position = getFilteredHeroId();
            }
        }


        //Heroes gets pictures
        LinearLayout imageOfHero = (LinearLayout) view.findViewById(R.id.picture);
        Bitmap heroScaledBitmap = scalePicture(position);
        //method of setting picture defined by the SDK version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageOfHero.setBackground(new BitmapDrawable(view.getResources(), heroScaledBitmap));
        } else {
            imageOfHero.setBackgroundDrawable(new BitmapDrawable(view.getResources(), heroScaledBitmap));
        }


        //Text for name insertion
        TextView nameOfHero = (TextView) view.findViewById(R.id.nameText);
        nameOfHero.setText(SuperHero.getAllHeroes().get(position).getName());

        //Time insertion
        TextView timeText = (TextView) view.findViewById(R.id.timeText);
        timeText.setText(SuperHero.getAllHeroes().get(position).getConvertedTime());

        Log.e("Loading", "updating with: " + SuperHero.getAllHeroes().size() + " heroes");
        final int id = position;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, SuperHeroActivity.class);
                intent.putExtra("idToShow", id);
                ctx.startActivity(intent);

            }
        });

        return view;


    }

    /**
     * Scales a picture for current position in list, for pperfect fit inside item
     *
     * @param position of item in list, for which picture is prepared
     * @return scaled picture
     */
    private Bitmap scalePicture(int position) {

        //getting screen size
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) ctx).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        // calculate scale that will be used for picture on this screen
        float scale = (float) SuperHero.getAllHeroes().get(position).getImage().getWidth() / (float) displaymetrics.widthPixels;

        // calculate perfect height for picture to be shown with normal aspect ratio
        int pictureHight = (int) (SuperHero.getAllHeroes().get(position).getImage().getHeight() / scale);

        //change size of picture with prepared width and height
        return Bitmap.createScaledBitmap(SuperHero.getAllHeroes().get(position).getImage(), displaymetrics.widthPixels, pictureHight, false);

    }

    /**
     * use in case of changes in heroes list
     */
    public void updateResults() {

        //Triggers the list update
        notifyDataSetChanged();
    }

    /**
     * handles filter
     *
     * @param filterText setup for filter
     */
    public void showOnlyFiltered(String filterText) {
        if (!filterText.equals("")) { // if filter is set, (not empty)
            this.filterText = filterText;
            isFilterSet = true;
            Log.e("filter", "filter is showing: " + filterText);
        } else { // if filter is empty marks to return to default, (show all items)
            this.filterText = "";
            isFilterSet = false;
        }
        updateResults();
    }

    /**
     * Can we found item with such name in list
     *
     * @return true if there is such element
     */
    private boolean isItemFilteredAvailiable() {

        for (int i = 0; i < SuperHero.getAllHeroes().size(); i++) {
            // compare names for each item
            if (filterText.toLowerCase().equals(SuperHero.getAllHeroes().get(i).getName().toLowerCase())) {
                return true;
            }
        } // end for
        return false;
    }

    /**
     * Search by names through items, and returns founded items
     *
     * @return item which was found on filter, or null if not found
     */
    private SuperHero getFilteredHero() {
        for (int i = 0; i < SuperHero.getAllHeroes().size(); i++) {
            // compare names for each item
            if (filterText.toLowerCase().equals(SuperHero.getAllHeroes().get(i).getName().toLowerCase())) {
                //item founded
                return SuperHero.getAllHeroes().get(i);
            }
        } // end for
        return null;
    }

    /**
     * id of item with searched name
     *
     * @return id in list of items, 0 if not found
     */
    private int getFilteredHeroId() {
        for (int i = 0; i < SuperHero.getAllHeroes().size(); i++) {
            // compare names for each item
            if (filterText.toLowerCase().equals(SuperHero.getAllHeroes().get(i).getName().toLowerCase())) {
                //id ofitem founded
                return i;
            }
        } // end for
        return 0;
    }
}
