package com.studmuffins.application;

import android.os.AsyncTask;
import java.util.HashMap;

import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;
import android.swedspot.scs.data.SCSShort;
import android.swedspot.scs.data.Uint8;

import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.automotiveapi.AutomotiveManager;
import com.swedspot.vil.policy.AutomotiveCertificate;


/**
 * Created by hari on 12/04/15.
 */
class AGASystem extends AsyncTask<Object, Object, Object> {

    public static HashMap <Integer, Float> map = new HashMap<Integer, Float>();
    public static float value;
    public static int signal;
    private static final AutomotiveCertificate amc = new AutomotiveCertificate(new byte[0]);

    protected Object doInBackground(Object...objects) {
        // Access to Automotive API
        AutomotiveListener aml = new AutomotiveListener() {
            @Override
            public void receive(final AutomotiveSignal automotiveSignal) {

                signal = automotiveSignal.getSignalId();

                if (signal == AutomotiveSignalId.FMS_CURRENT_GEAR) {
                    value = ((SCSShort) automotiveSignal.getData()).getShortValue();
                } else
                if (signal == AutomotiveSignalId.FMS_BRAKE_SWITCH) {
                    value = ((Uint8) automotiveSignal.getData()).getIntValue();
                } else {
                    value = ((SCSFloat) automotiveSignal.getData()).getFloatValue();
                }

                map.put(signal, value);
            }

            @Override
            public void timeout(int i) {
            }

            @Override
            public void notAllowed(int i) {
            }
        };

        AutomotiveManager manager = AutomotiveFactory.createAutomotiveManagerInstance(amc, aml, null);
        manager.register(
                AutomotiveSignalId.FMS_INSTANTANEOUS_FUEL_ECONOMY,
                AutomotiveSignalId.FMS_ENGINE_SPEED,
                AutomotiveSignalId.FMS_CURRENT_GEAR,
                AutomotiveSignalId.FMS_WHEEL_BASED_SPEED,
                AutomotiveSignalId.FMS_BRAKE_SWITCH);
        manager.setListener(aml);
        return null;
    }
}

