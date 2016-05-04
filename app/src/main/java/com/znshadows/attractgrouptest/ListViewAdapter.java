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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kostya on 04.05.2016.
 */
public class ListViewAdapter extends BaseAdapter {

    LayoutInflater lInflater;
    Context ctx;

    public ListViewAdapter(Context context) {

        ctx = context;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return SuperHero.getAllHeroes().size();
    }

    @Override
    public Object getItem(int position) {
        return SuperHero.getAllHeroes().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.main_list_item, parent, false);
        }


        //Heroes gets pictures
        LinearLayout imageOfHero = (LinearLayout) view.findViewById(R.id.picture);
        Bitmap heroScaledBitmap = scalePicture(position);
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

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, SuperHeroActivity.class);
                intent.putExtra("idToShow", position);
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
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) ctx).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displaymetrics);
        float aspectRatio = (float) SuperHero.getAllHeroes().get(position).getImage().getWidth() / (float) displaymetrics.widthPixels;
        return Bitmap.createScaledBitmap(SuperHero.getAllHeroes().get(position).getImage(), displaymetrics.widthPixels, (int) (SuperHero.getAllHeroes().get(position).getImage().getHeight() / aspectRatio), false);

    }

    /**
     * use in case of changes in heroes list
     */
    public void updateResults() {

        //Triggers the list update
        notifyDataSetChanged();
    }
}
