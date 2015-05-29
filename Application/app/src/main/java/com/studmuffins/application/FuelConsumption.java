package com.studmuffins.application;

/**
 * Created by hari on 08/04/15.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FuelConsumption extends Fragment {
    private FuelUI ui;
    private Handler mHandler = new Handler();
    private AGASystem aga = new AGASystem();

    private float sendSignal;
    private float progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fuel_fragment, container, false);
        ui = (FuelUI) view.findViewById(R.id.UI);

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    autoListener();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return view;
    }

    private void autoListener() {
        Float signal = aga.map.get(AutomotiveSignalId.FMS_INSTANTANEOUS_FUEL_ECONOMY);

        if (signal != null) {
            sendSignal = signal;
            progress = (sendSignal/125) * 100;
        }



        // Post the Percentage of fuel and the metrics to FuelUI
        mHandler.post(new Runnable() {
            public void run() {
                ui.setClipping(progress, sendSignal);
            }
        });
    }

    public int getFuel(int point) {
        point = (int) sendSignal * 100;
        return point;
    }
}
