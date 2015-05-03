package com.studmuffins.application;

import android.os.AsyncTask;
import java.util.HashMap;

import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;

import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.automotiveapi.AutomotiveManager;
import com.swedspot.vil.policy.AutomotiveCertificate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




/**
 * Created by hari on 12/04/15.
 */
public class AGASystem {

    public static HashMap <Integer, Float> map = new HashMap<>();
    public static float value;
    public static int signal;
    private static final AutomotiveCertificate amc = new AutomotiveCertificate(new byte[0]);
    private AutomotiveManager manager;
    public static ExecutorService executorService = Executors.newSingleThreadExecutor();
    //if (map.containsKey(AutomotiveSignalId.FMS_INSTANTANEOUS_FUEL_ECONOMY)) {
        //value = (SCSFloat) automotiveSignal.getData();

        //System.out.println("SignalID: " + AutomotiveSignalId.FMS_INSTANTANEOUS_FUEL_ECONOMY + " - AGA:"+  value);
        //}

        // Printing out hashmap
                    /*System.out.println("Hash Map O/P");
                    Iterator it = map.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        System.out.println(pair.getKey() + " = " + pair.getValue());
                        it.remove(); // avoids a ConcurrentModificationException
                    }*/

    public AGASystem() {
        // Access to Automotive API
        AutomotiveListener aml = new AutomotiveListener() {
            @Override
            public void receive(final AutomotiveSignal automotiveSignal) {

                //System.out.println("PING!");
                signal = automotiveSignal.getSignalId();
                value = ((SCSFloat) automotiveSignal.getData()).getFloatValue();
                map.put(signal, value);
                //System.out.println(" signal: " + signal +" value: " + value);

            }

            @Override
            public void timeout(int i) {
            }

            @Override
            public void notAllowed(int i) {
            }
        };
        manager = AutomotiveFactory.createAutomotiveManagerInstance(amc, aml, null);
        doRegister();
        manager.setListener(aml);
    }

    public void addListener(int listener) {
        //System.out.println(listener);
        map.put(listener, value);
        //doRegister(listener);
    }
    public void doRegister() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                manager.register(
                        AutomotiveSignalId.FMS_INSTANTANEOUS_FUEL_ECONOMY,
                        AutomotiveSignalId.FMS_ENGINE_SPEED);

            }
        });
    }
}

