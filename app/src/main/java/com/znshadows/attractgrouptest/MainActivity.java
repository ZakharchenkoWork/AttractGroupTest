package com.znshadows.attractgrouptest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.znshadows.attractgrouptest.dataLoading.JSONLoader;
import com.znshadows.attractgrouptest.dataLoading.Loader;

public class MainActivity extends AppCompatActivity {
private static final String DATA_SOURCE = "http://others.php-cd.attractgroup.com/test.json"; // URL with data


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // if there is no internet, there is no point to lunch application
        if(isNetworkAvailable())
        {
            Toast.makeText(this,"connected", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"Not connected", Toast.LENGTH_LONG).show();
            close();
        }
        //hides keybord on start, otherwise it is anoyng at start, it apears because of the filter field on the main screen
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //preparing list with data
        final ListView listView = (ListView) findViewById(R.id.listView);
        final ListViewAdapter adapter = new ListViewAdapter(MainActivity.this);
        listView.setAdapter(adapter);
        //as soon as firt part of data will be loaded, list will be updated
        Loader load = new Loader(DATA_SOURCE, new JSONLoader.OnLoadFinishListener() {
            @Override
            public void onFinish() {
                adapter.updateResults();

            }
        });
        load.execute();
        final EditText filter = (EditText) findViewById( R.id.filter);
        filter.addTextChangedListener(new TextWatcher() {
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
        });


        String[] items = {getString(R.string.menu_item1), getString(R.string.menu_item2), getString(R.string.menu_item3),getString(R.string.menu_item4)};
        int[] groups = {2,2};

        if (findViewById(R.id.drawer_layout) == null) {

            ListView leftDrawerList = (ListView)findViewById(R.id.left_drawer);
            leftDrawerList.setAdapter(new GroupedDrawerAdapter(this, items, groups ));
        } else {
            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            ListView leftDrawerList = (ListView)findViewById(R.id.left_drawer);
            leftDrawerList.setAdapter(new GroupedDrawerAdapter(this, items, groups ));
        }



    }

    /**
     * checks if the device connected to the internet
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
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
