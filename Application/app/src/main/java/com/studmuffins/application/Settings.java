package com.studmuffins.application;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

/**
 * Created by Admin on 2015-05-19.
 */
public class Settings extends BaseActivity {

    private String[] navMenuTitles;
   // private Switch switch1, switch2;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        set(navMenuTitles);
        //addListenerOnButton();
    }

  /*  public void addListenerOnButton(){
        switch1 = (Switch) findViewById(R.id.voice);
        switch2 = (Switch) findViewById(R.id.rest);
        switch1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });*/
    }




   // public void OnToggleClicked(View view) {
       // Is the toggle on?
     //   boolean on = ((Switch) view).isChecked();

       // if (on) {

        //} else {

        //}
    //}


