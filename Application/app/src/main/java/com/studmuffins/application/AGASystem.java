package com.studmuffins.application;


import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.AsyncTask;

import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;

import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.automotiveapi.AutomotiveManager;
import com.swedspot.vil.distraction.DriverDistractionLevel;
import com.swedspot.vil.distraction.DriverDistractionListener;
import com.swedspot.vil.distraction.LightMode;
import com.swedspot.vil.distraction.StealthMode;
import com.swedspot.vil.policy.AutomotiveCertificate;


/**
 * Created by hari on 12/04/15.
 */
public class AGASystem extends AsyncTask <Void, Void, Void> {

    public TextView text;
    public ProgressBar progBar;
    public int fuel = 0;

    @Override
    protected Void doInBackground(Void[] params) {
        // set up AutomotiveManager (Step 6) here

        AutomotiveListener autoListener = new AutomotiveListener() {
            /* code from Step 4 */
            // Listener that observes the Signals
            @Override
            public void receive(final AutomotiveSignal automotiveSignal) {
                System.out.println("Ping!");
                text.post(new Runnable() { // Post the result back to the View/UI thread

                    public void run() {
                        text.setText(String.format("%.1f km/l", ((SCSFloat) automotiveSignal.getData()).getFloatValue()));
                        int aVal = Math.round(((SCSFloat) automotiveSignal.getData()).getFloatValue());
                        if (aVal == 0) {
                            aVal = 1;
                        }
                        Integer bVal = (100/aVal)*1000;
                        //progBar.setImageLevel(bVal);
                        fuel = bVal;
                    }
                });
            }

            @Override
            public void timeout(int i) {

            }

            @Override
            public void notAllowed(int i) {

            }
        };

                                        /* code from Step 5 */
        DriverDistractionListener driverListener = new DriverDistractionListener() {
            // Observe driver distraction level
            @Override
            public void levelChanged(final DriverDistractionLevel driverDistractionLevel) {
                text.post(new Runnable() {
                    // Post the result back to the View/UI thread
                    public void run() {
                        text.setTextSize(driverDistractionLevel.getLevel() * 10.0F + 12.0F);
                    }
                });
            }

            @Override
            public void lightModeChanged(LightMode lightMode) {

            }

            @Override
            public void stealthModeChanged(StealthMode stealthMode) {

            }
        };
        AutomotiveManager manager1 = AutomotiveFactory.createAutomotiveManagerInstance(new AutomotiveCertificate(new byte[0]), autoListener, driverListener);
        manager1.register(AutomotiveSignalId.FMS_INSTANTANEOUS_FUEL_ECONOMY); // Register for the speed signal
        return null;
    }
}

