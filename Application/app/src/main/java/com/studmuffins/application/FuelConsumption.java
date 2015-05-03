package com.studmuffins.application;

/**
 * Created by hari on 08/04/15.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.swedspot.automotiveapi.AutomotiveManager;

public class FuelConsumption extends Fragment {
    private FuelUI ui;
    private TextView text;
    private Handler mHandler = new Handler();
    private static float mProgressStatus;
    private AGASystem aga = new AGASystem();
    private boolean newSwitch = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fuel_fragment, container, false);
        ui = (FuelUI) view.findViewById(R.id.UI);
        ui.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        text = (TextView) view.findViewById(R.id.Value);
        dosomething();
        return view;
    }

    public void dosomething() {

        new Thread(new Runnable() {
            public void run() {
                final int presentage = 0;
                while (mProgressStatus < 100) {
                    mProgressStatus += 1;
                    //mProgressStatus = Math.round(aga.value);
                    //Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            //ui.setClipping(mProgressStatus);
                            text.setText(""+mProgressStatus+"%");
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
