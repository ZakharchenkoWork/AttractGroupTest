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
    private Context ctx;
    private LayoutInflater lInflater;
    private int[] groups; // groups sorted. id in array means nummber of group, value number of items in group
    private String[] items; // items from which data should be shown
    private boolean isDividerNext = false; // is the next step should be divider flag

    /**
     * Special Adapter for Navigational menu which will draw dividers between groups of elements
     *
     * @param context context of an app
     * @param items   items to be add
     * @param groups  array which size is number of groups, and values are numbers of items inside this groups
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
        // divder is also a view, so for total number of items is desribed as all text items plus
        // all dividers, minus one because we don't need divider at the end.
        return items.length + groups.length - 1;
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
        //inserting empty view
        if (view == null) {
            view = lInflater.inflate(R.layout.navigation_menu_item, parent, false);
        }
        //if we have to show items now (not divider)
        if (!isDividerNext) {
            int groupsSum = 0;
            int numberOfDividers = 0;
            // It is some math magic, please don't ask, i hadn't sleep all night,
            // I wrote this code at 0700 am, and started to write this comment at 1400, still not sleeping
            //I just want to get a job. and sleep. Please.
            for (int groupNumber = 0; groupNumber < groups.length; groupNumber++) {
                Log.d("adapter", "j = " + groupNumber);
                //Sum of the element in groups,
                groupsSum += groups[groupNumber];
                Log.d("adapter", "groupSum = " + groupsSum);
                // we have dividers after each group, so if our position
                // without dividers is right at the end of group
                // we insert a divider on the next position
                if (position - groupNumber == groupsSum - 1) {
                    Log.d("adapter", "position = " + position);
                    isDividerNext = true;
                }
                // As we iterrate over groups. if our position is less then sum of group elements by now
                // we knw that our position belongs to current group
                if (position <= groupsSum)
                {
                    numberOfDividers = groupNumber;
                    Log.d("adapter", "groupNum again = " + groupNumber);
                    break;
                } // 0
            }

            TextView itemText = (TextView) view.findViewById(R.id.itemText);
            Log.d("adapter", "items pos, groupNumber = " + "pos = " + position + " - groupNumber = " + numberOfDividers);
            // our position in array of valeus equals position in ListView minus dividers, as they are displayed as elements of ListView
            itemText.setText(items[position - numberOfDividers]);

        } else {
            //adding a divider as
            Log.d("divider", "divider added");
            view = insertDivider(view);
            isDividerNext = false;

        }
        return view;
    }

    /**
     *
     * @param view
     * @return
     */
    private View insertDivider(View view) {
        view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5));
        view.setBackgroundColor(Color.WHITE);
        return view;
    }
}
