package com.znshadows.attractgrouptest;

/**
 * Created by kostya on 05.05.2016.
 */
import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PageFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    int pageNumber;


    static PageFragment newInstance(int page) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        //int id = getIntent().getIntExtra("idToShow",0);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_super_hero, null);

        //TextView tvPage = (TextView) view.findViewById(R.id.tvPage);
        //tvPage.setText("Page " + pageNumber);
        //tvPage.setBackgroundColor(backColor);
       // int id = getIntent().getIntExtra("idToShow",0);
        ImageView heroPicture = (ImageView) view.findViewById(R.id.imageView);
        heroPicture.setImageBitmap(SuperHero.getAllHeroes().get(pageNumber).getImage());
        TextView itemId = (TextView) view.findViewById(R.id.itemId);
        itemId.setText(""+SuperHero.getAllHeroes().get(pageNumber).getItemId());
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(SuperHero.getAllHeroes().get(pageNumber).getName());
        TextView time = (TextView) view.findViewById(R.id.time);
        time.setText(SuperHero.getAllHeroes().get(pageNumber).getConvertedTime());
        TextView description = (TextView) view.findViewById(R.id.description);
        description.setText(SuperHero.getAllHeroes().get(pageNumber).getDescription());
        return view;
    }
}