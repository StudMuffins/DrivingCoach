package com.studmuffins.application;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * Created by Zoe on 19/05/2015.
 */
public class AddNewCar extends BaseActivity {
    private String[] navMenuTitles;
    EditText carNameText;
    RadioButton manual, auto;
    RadioButton petrol, diesel, gas, el;
    RadioButton[] transmission;
    RadioButton[] fuel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcar);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        set(navMenuTitles);

        carNameText = (EditText)findViewById(R.id.text_carname);
        manual = (RadioButton)findViewById(R.id.radioButton_manual);
        auto = (RadioButton)findViewById(R.id.radioButton_auto);
        petrol = (RadioButton)findViewById(R.id.radioButton_petrol);
        diesel = (RadioButton)findViewById(R.id.radioButton_diesel);
        gas = (RadioButton)findViewById(R.id.radioButton_gas);
        el = (RadioButton)findViewById(R.id.radioButton_el);
        addRB();
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

    public void saveCar(View v) {
        String carName = carNameText.getText().toString();


        //System.out.println(carName);
    }
    protected void getCheckedRB() {
        for (int i = 0; i < transmission.length; i++) {
            if(transmission[i].isChecked()) {
                //String t = transmission[i].getText();
            }
        }

    }
    protected void addRB() {
        transmission[0] = manual;
        transmission[1] = auto;

        fuel[0] = petrol;
        fuel[1] = diesel;
        fuel[2] = gas;
        fuel[3] = el;
    }
}
