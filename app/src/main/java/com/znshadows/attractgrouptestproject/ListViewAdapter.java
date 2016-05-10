package com.znshadows.attractgrouptestproject;

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

import com.znshadows.attractgrouptestproject.data.Filter;


/**
 * Created by kostya on 04.05.2016.
 */
public class ListViewAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater lInflater;

    private Filter filter;

    public ListViewAdapter(Context context, Filter filter) {
        this.filter = filter;
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
        return filter.getFilteredHeroes().size();
    }

    /**
     * also helps to show filtered result
     *
     * @return Object(item) which shold be used in this iteration
     */
    @Override
    public Object getItem(int position) {
        //normal items one by one to list
        return filter.getFilteredHeroes().get(position);
    }

    /**
     * also helps to show filtered result
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        //inserting empty view
        if (view == null) {
            view = lInflater.inflate(R.layout.main_list_item, parent, false);
        }

        //Heroes gets pictures
        LinearLayout imageOfHero = (LinearLayout) view.findViewById(R.id.picture);
        Bitmap heroScaledBitmap = rescale(position, parent.getWidth());
        //method of setting picture defined by the SDK version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageOfHero.setBackground(new BitmapDrawable(view.getResources(), heroScaledBitmap));
        } else {
            imageOfHero.setBackgroundDrawable(new BitmapDrawable(view.getResources(), heroScaledBitmap));
        }


        //Text for name insertion
        TextView nameOfHero = (TextView) view.findViewById(R.id.nameText);
        nameOfHero.setText(filter.getFilteredHeroes().get(position).getName());

        //Time insertion
        TextView timeText = (TextView) view.findViewById(R.id.timeText);
        timeText.setText(filter.getFilteredHeroes().get(position).getConvertedTime());

        Log.e("Loading", "updating with: " + filter.getFilteredHeroes().size() + " heroes");
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


    private Bitmap scalePicture(int position) {

        //getting screen size
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) ctx).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        // calculate scale that will be used for picture on this screen
        float scale = (float) filter.getFilteredHeroes().get(position).getImage().getWidth() / (float) displaymetrics.widthPixels;

        // calculate perfect height for picture to be shown with normal aspect ratio
        int pictureHeight = (int) (filter.getFilteredHeroes().get(position).getImage().getHeight() / scale);

        //change size of picture with prepared width and height
        return Bitmap.createScaledBitmap(filter.getFilteredHeroes().get(position).getImage(), displaymetrics.widthPixels, pictureHeight, false);

    }

    /**
     * Scales a picture for current position in list, for perfect fit inside available space
     *
     * @param position of item in list, for which picture is prepared
     * @param width    of view that represents available space
     * @return scaled picture
     */
    private Bitmap rescale(int position, float width) {

        //in case of change orientation, screen width will be 0, untill screen completely loaded.
        //After loadng e will refresh ListView any way
        if (width > 0) {
            // calculate scale that will be used for picture on this screen
            float scale = (float) filter.getFilteredHeroes().get(position).getImage().getWidth() / width;

            // calculate perfect height for picture to be shown with normal aspect ratio
            int pictureHeight = (int) (filter.getFilteredHeroes().get(position).getImage().getHeight() / scale);


            //change size of picture with prepared width and height
            return Bitmap.createScaledBitmap(filter.getFilteredHeroes().get(position).getImage(), (int) width, pictureHeight, false);
        } else {
            return filter.getFilteredHeroes().get(position).getImage();
        }
    }

    /**
     * use in case of changes in heroes list
     */
    public void updateList(Filter filter) {
        this.filter = filter;
        //Triggers the list update
        notifyDataSetChanged();
    }


    /**
     * is this item, matches filter
     *
     * @param filterText string to match item name
     * @param position   position of item in list
     * @return true if there is a match
     */
    private boolean isItemFiltered(String filterText, int position) {
        if (filter.getFilteredHeroes().get(position).getName().toLowerCase().contains(filterText.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Used to get total number of items that matches filter
     *
     * @param filterText string to match item name
     * @return number of items found
     */
    private int getFilterMatchesCount(String filterText) {
        int result = 0;
        for (int i = 0; i < filter.getFilteredHeroes().size(); i++) {
            if (isItemFiltered(filterText, i)) {
                result++;
            }
        }
        return result;
    }
}
