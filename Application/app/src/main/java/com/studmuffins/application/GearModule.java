package com.studmuffins.application;

/**
 * Created by hari on 23/03/15.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;
import android.swedspot.automotiveapi.AutomotiveSignalId;

public class GearModule extends Fragment {

    private GearUI ui;
    private Float text;
    private Handler mHandler = new Handler();
    private Float signal;
    private float sendSignal;
    private float sendText;
    private float progress;
    private AGASystem aga = new AGASystem();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gear_fragment, container, false);
        ui = (GearUI) view.findViewById(R.id.UI);
        ui.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        autoListener();
        return view;
    }

    public void autoListener() {

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    signal = aga.map.get(AutomotiveSignalId.FMS_ENGINE_SPEED);
                    text = aga.map.get(AutomotiveSignalId.FMS_CURRENT_GEAR);


                    if(signal != null) {
                        sendSignal = signal;
                    }
                    if(text != null) {
                        sendText = text;
                    }

                    progress = (sendSignal / 10000) * 100;

                    mHandler.post(new Runnable() {
                        public void run() {
                            ui.setClipping(progress, sendText);
                        }
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
