package com.znshadows.attractgrouptestproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.znshadows.attractgrouptestproject.data.SuperHero;

/**
 * Created by kostya on 04.05.2016.
 */
public class SuperHeroActivity extends FragmentActivity {


    static int PAGE_COUNT = SuperHero.getAllHeroes().size();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        //preparing Swiping sreens
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        //adapter wwhich will manage our swaping
        pager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        //setts item to be shown on start (item which were clickd on previous screen)
        pager.setCurrentItem( getIntent().getIntExtra("idToShow", 0) );
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

    }

}