package com.znshadows.attractgrouptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private static final String DATA_SOURCE = "http://others.php-cd.attractgroup.com/test.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listView = (ListView) findViewById(R.id.listView);
        final ListViewAdapter adapter = new ListViewAdapter(MainActivity.this);
        listView.setAdapter(adapter);

        JSONLoader load = new JSONLoader(DATA_SOURCE, new JSONLoader.OnLoadFinishListener() {
            @Override
            public void onFinish() {

                adapter.updateResults();



            }
        });
        load.execute();

    }
}
