package com.studmuffins.application;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;



/**
 * Created by Pooria on 19/05/15.
 */
public class Settings extends BaseActivity {

    private String[] navMenuTitles;
    Switch s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        s = (Switch) findViewById(R.id.voice);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        set(navMenuTitles);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Toast.makeText(this, "The Switch is " + (isChecked ? "on" : "off"),
                //   Toast.LENGTH_SHORT).show();
                if (isChecked) {


                } else {
                    System.out.println("Not Checked");
                }
            }
        });
    }
}
/*
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this, "The Switch is " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();
        if(isChecked) {
            System.out.println("Checked");
        } else {
            System.out.println("Not Checked");
        }
    }
*/
  /*  public Settings(String[] navMenuTitles) {
        this.navMenuTitles = navMenuTitles;
    }

    s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        }
    });*/







           // FragmentManager fm = getFragmentManager();
           // addShowHideListener(R.id.gear, fm.findFragmentById(R.id.gearFrag));

/*
        void addShowHideListener(int SwitchID, final Fragment fragment) {
          final Switch gearSwitch = (Switch) findViewById(SwitchID);
        gearSwitch.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {


            }
        });
        }
*/
