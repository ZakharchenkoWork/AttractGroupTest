package com.znshadows.attractgrouptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kostya on 04.05.2016.
 */
public class SuperHeroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_hero);


        // To retrieve object in second Activity
        int id = getIntent().getIntExtra("idToShow",0);
        ImageView heroPicture = (ImageView) findViewById(R.id.imageView);
        heroPicture.setImageBitmap(SuperHero.getAllHeroes().get(id).getImage());
        TextView itemId = (TextView) findViewById(R.id.itemId);
        itemId.setText(""+SuperHero.getAllHeroes().get(id).getItemId());
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(SuperHero.getAllHeroes().get(id).getName());
        TextView time = (TextView) findViewById(R.id.time);
        time.setText(SuperHero.getAllHeroes().get(id).getConvertedTime());
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(SuperHero.getAllHeroes().get(id).getDescription());



    }
}
