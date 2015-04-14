package com.studmuffins.application;

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

    public static float value;
    private static final AutomotiveCertificate amc = new AutomotiveCertificate(new byte[0]);
    AutomotiveManager manager;

    @Override
    protected Void doInBackground(Void[] params) {
        // Access to Automotive API
        AutomotiveListener aml = new AutomotiveListener() {
            @Override
            public void receive(final AutomotiveSignal automotiveSignal) {
                System.out.println("PING!");
                value = (((SCSFloat) automotiveSignal.getData()).getFloatValue());
                System.out.println("AGA"+value);

            }

            @Override
            public void timeout(int i) {
            }

            @Override
            public void notAllowed(int i) {
            }
        };

        manager = AutomotiveFactory.createAutomotiveManagerInstance(amc, aml, null);
        manager.register(AutomotiveSignalId.FMS_INSTANTANEOUS_FUEL_ECONOMY); // Register for the speed signal
        manager.setListener(aml);
        return null;
    }
}

