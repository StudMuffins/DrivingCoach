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
    RadioButton[] transmission, fuel;
    MySQLiteHelper db;


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

       transmission = new RadioButton[] {manual, auto};
       fuel = new RadioButton[] {petrol, diesel, gas, el};

       db = new MySQLiteHelper(this);

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
        String transType = getCheckedRBTrans();
        String fuelType = getCheckedRBFuel();

        //System.out.println(carName + " " + transType + " " + fuelType);

        db.addCar(new Car(carName, transType, fuelType));

        System.out.println(db.getAllCars());

    }
    protected String getCheckedRBTrans() {
        for(int i = 0; i < transmission.length; i ++) {
            if(transmission[i].isChecked())
                return transmission[i].getText().toString();
        }
        return null;
    }
    protected String getCheckedRBFuel() {
        for(int i = 0; i < fuel.length; i++) {
            if(fuel[i].isChecked())
                return fuel[i].getText().toString();
        }
        return null;
    }
}
