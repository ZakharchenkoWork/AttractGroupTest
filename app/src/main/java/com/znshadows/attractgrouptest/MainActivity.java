package com.znshadows.attractgrouptest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.znshadows.attractgrouptest.dataLoading.JSONLoader;
import com.znshadows.attractgrouptest.dataLoading.Loader;

/**
 * Created by kostya on 07.05.2016.
 */
public class MainActivity extends AppCompatActivity {
    private static final String DATA_SOURCE = "http://others.php-cd.attractgroup.com/test.json"; // URL with data
    private static boolean isDataLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Preparation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //hides keybord on start, otherwise it is anoyng at start, it apears because of the filter field on the main screen
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //preparing list with data
        final ListView listView = (ListView) findViewById(R.id.listView);
        final ListViewAdapter adapter = new ListViewAdapter(MainActivity.this);
        listView.setAdapter(adapter);

        //prevents from reloading data in case of change orientation
        if (!isDataLoaded) {

            // if there is no internet, there is no point to lunch application
            if (isNetworkAvailable()) {
                Toast.makeText(this, "connected", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Not connected", Toast.LENGTH_LONG).show();
                close();
            }
            //as soon as firt part of data will be loaded, list will be updated
            Loader load = new Loader(DATA_SOURCE, new JSONLoader.OnLoadFinishListener() {
                @Override
                public void onFinish() {
                    adapter.updateResults();
                    isDataLoaded = true;

                }
            });
            load.execute();
        }
        final EditText filter = (EditText) findViewById(R.id.filter);
        filter.addTextChangedListener(new EditTextListener(adapter));

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }


    /**
     * checks if the device connected to the internet
     *
     * @return tue if device is connected
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * closes MainActivity, which means closing of the application
     */
    public void close() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    /**
     * listener for filter
     */
    private class EditTextListener implements TextWatcher {
        ListViewAdapter adapter;

        private EditTextListener(ListViewAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            adapter.showOnlyFiltered(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}