package com.znshadows.attractgrouptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private static final String DATA_SOURCE = "http://others.php-cd.attractgroup.com/test.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
    }


}
