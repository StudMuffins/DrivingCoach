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
import com.swedspot.vil.policy.AutomotiveCertificate;


/**
 * Created by hari on 12/04/15.
 */
public class AGASystem extends AsyncTask <Void, Void, Void> {

    public int value;

    @Override
    protected Void doInBackground(Void[] params) {
        // Access to Automotive API
        AutomotiveListener autoListener = new AutomotiveListener() {
            @Override
            public void receive(final AutomotiveSignal automotiveSignal) {
                System.out.println("PING!");
                value = Math.round(((SCSFloat) automotiveSignal.getData()).getFloatValue());
                System.out.println(value);

            }

            @Override
            public void timeout(int i) {
            }

            @Override
            public void notAllowed(int i) {
            }
        };

        AutomotiveManager manager = AutomotiveFactory.createAutomotiveManagerInstance(new AutomotiveCertificate(new byte[0]), autoListener, null);
        manager.register(AutomotiveSignalId.FMS_INSTANTANEOUS_FUEL_ECONOMY); // Register for the speed signal
        return null;
    }
}

