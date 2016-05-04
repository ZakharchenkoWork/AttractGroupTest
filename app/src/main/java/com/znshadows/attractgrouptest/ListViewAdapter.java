package com.znshadows.attractgrouptest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        ImageView imageOfHero = (ImageView) view.findViewById(R.id.imageView);
        imageOfHero.setImageBitmap(heroes.get(position).getImage());
        //Text for name insertion
        TextView nameOfHero = (TextView) view.findViewById(R.id.nameText);
        nameOfHero.setText(heroes.get(position).getName());
        //Time insertion
        //TextView timeText = (TextView) view.findViewById(R.id.nameText);

        Log.e("Loading", "updating with: " + heroes.size() + " heroes");


        return view;
    }

    public void updateResults(ArrayList<SuperHero> heroes) {
        this.heroes = heroes;
        //Triggers the list update
        notifyDataSetChanged();
    }
}
