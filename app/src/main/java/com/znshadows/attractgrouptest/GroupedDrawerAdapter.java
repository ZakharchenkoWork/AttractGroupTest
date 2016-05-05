package com.znshadows.attractgrouptest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by kostya on 05.05.2016.
 */
public class GroupedDrawerAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    int[] groups;
    String[] items;
    boolean isDividerNext = false;
    /**
     * Special Adapter for Navigational menu which will draw dividers between groups of elements
     * @param context context of an app
     * @param items items to be add
     * @param groups array which size is number of groups, and values are numbers of items inside this groups
     */
    public GroupedDrawerAdapter(Context context, String[] items, int[] groups) {
        this.groups = groups;
        this.items = items;
        ctx = context;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.length + groups.length-1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.navigation_menu_item, parent, false);
        }
        if(!isDividerNext) {
            int groupsSum = 0;
            int groupNumber = 0;
            for (int j = 0; j < groups.length; j++) {
            Log.d("adapter" , "j = " + j);
                groupsSum += groups[j]; // 2 // 3
                Log.d("adapter" , "groupSum = " + groupsSum);
                if (position - j == groupsSum - 1 ) { // 1 //2
                    Log.d("adapter" , "position = " + position);
                    isDividerNext = true;
                }
                if(position <= groupsSum) // (0,1) < 2 // pos(2) - divider // 2 < 3
                {groupNumber = j;
                    Log.d("adapter" , "groupNum again = " + groupNumber);
                    break;} // 0
            }

            TextView itemText = (TextView) view.findViewById(R.id.itemText);
            Log.d("adapter" , "items pos, groupNumber = " + "pos = " + position + " - groupNumber = " + groupNumber  );
            itemText.setText(items[position - groupNumber]); //0,1  - 0//

        } else {
            Log.d("divider", "divider added");
            view = insertDivider(view);
            isDividerNext = false;

        }
        return view;
    }
private View insertDivider(View view) {
    view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,5));
    view.setBackgroundColor(Color.WHITE);
   return view;
}
}
