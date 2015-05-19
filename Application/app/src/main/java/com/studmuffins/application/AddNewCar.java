package com.studmuffins.application;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Zoe on 19/05/2015.
 */
public class AddNewCar extends BaseActivity {
    private String[] navMenuTitles;
    TTSManager ttsManager = null;
    EditText carNameText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcar);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        set(navMenuTitles);

        carNameText = (EditText)findViewById(R.id.text_carname);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveCarName(View v) {
        String carName = carNameText.getText().toString();
        //System.out.println(carName);
    }
}
