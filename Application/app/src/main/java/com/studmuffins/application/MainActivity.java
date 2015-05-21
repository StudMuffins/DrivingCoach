package com.studmuffins.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.preference.PreferenceActivity;


public class MainActivity extends BaseActivity {

    private String[] navMenuTitles;
    private GearModule gearMod;
    private String text;
    private String cloneText;
    TTSManager ttsManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        gearMod = (GearModule) getFragmentManager().findFragmentById(R.id.gearFrag);

        set(navMenuTitles);
        new AGASystem().execute();
        ttsManager = new TTSManager();
        ttsManager.init(this);

        new Thread(new Runnable() {
            public void run() {
                cloneText = null;
                while (true) {

                    checkInputs();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    public void checkInputs() {
        text = gearMod.gearChange();
        if (text != cloneText) {
            ttsManager.addQueue(text);
            cloneText = text;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
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

    public void showProgress(View v) {
        startActivity(new Intent(MainActivity.this, ProgressTracking.class));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ttsManager.shutDown();
    }

}
