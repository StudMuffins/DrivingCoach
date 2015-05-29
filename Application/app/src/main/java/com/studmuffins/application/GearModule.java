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
    private Float gear;
    private Handler mHandler = new Handler();
    private float sendGear;
    private float progress;
    private float prevGear;
    private int gearPoint;
    private AGASystem aga = new AGASystem();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gear_fragment, container, false);
        ui = (GearUI) view.findViewById(R.id.UI);
        ui.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        prevGear = 0;

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
        Float signal = aga.map.get(AutomotiveSignalId.FMS_ENGINE_SPEED);
        gear = aga.map.get(AutomotiveSignalId.FMS_CURRENT_GEAR);

        // Once signal is != null then calculate the percentage of the current RPM
        if(signal != null) {
            progress = (signal / 10000) * 100;
        }
        if(gear != null) {
            sendGear = gear;
        }

        // Post the Percentage of RPM and current gear to GearUI
        mHandler.post(new Runnable() {
            public void run() {
                ui.setClipping(progress, sendGear);
            }
        });
    }

    // Return a string to be used for voice feedback
    // when specific conditions are met
    public String gearChange() {
        String text = null;

        if(prevGear == sendGear) {
            if (progress >= 20 && progress < 25) {
                text = "Shift Up";

                if (sendGear > prevGear) {
                    gearPoint += 1;
                }
            } else
            if (progress >= 25 && progress <= 30) {
                text = "Shift Up" + "Skip one Gear";

                if (sendGear > prevGear) {
                    gearPoint += 2;
                }
            } else
            if (progress > 85) {
                text = "Warning";
            }
        } else {
            prevGear = sendGear;
        }

        if (progress > 30) {
            if (sendGear > prevGear) {
                gearPoint -= 2;
            }
        }

        return text;
    }

    public int getGear(int point) {
        point = gearPoint;
        return point;
    }
}
