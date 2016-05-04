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
    private ArrayList<SuperHero> heroes = null;
    LayoutInflater lInflater;
    Context ctx;

    public ListViewAdapter(Context context, ArrayList<SuperHero> heroes) {
        this.heroes = heroes;
        ctx = context;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return heroes.size();
    }

    @Override
    public Object getItem(int position) {
        return heroes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        nameOfHero.setText(heroes.get(position).getName());
        //Time insertion
        TextView timeText = (TextView) view.findViewById(R.id.timeText);
        timeText.setText(heroes.get(position).getConvertedTime());

        Log.e("Loading", "updating with: " + heroes.size() + " heroes");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, SuperHeroActivity.class);
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
        float aspectRatio = (float) heroes.get(position).getImage().getWidth() / (float) displaymetrics.widthPixels;
        return Bitmap.createScaledBitmap(heroes.get(position).getImage(), displaymetrics.widthPixels, (int) (heroes.get(position).getImage().getHeight() / aspectRatio), false);
    }

    /**
     * use in case of changes in heroes list
     *
     * @param heroes list of heroes
     */
    public void updateResults(ArrayList<SuperHero> heroes) {
        this.heroes = heroes;
        //Triggers the list update
        notifyDataSetChanged();
    }
}
