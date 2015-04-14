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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.swedspot.automotiveapi.AutomotiveManager;

public class FuelConsumption extends Fragment {
    private ProgressBar progBar;
    private TextView text;
    private Handler mHandler = new Handler();
    private int mProgressStatus = 0;
    private AGASystem aga = new AGASystem();
    private AutomotiveManager manager1 = aga.manager;
    private boolean newSwitch = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fuel_fragment, container, false);
        progBar = (ProgressBar) view.findViewById(R.id.progressBar);
        text = (TextView) view.findViewById(R.id.Value);
        //System.out.println(aga.getValue());
        dosomething();
        return view;
    }

    public void dosomething() {

        new Thread(new Runnable() {
            public void run() {
                final int precentage = 0;
                while (newSwitch == true) {
                    mProgressStatus = Math.round(aga.value);
                    //mProgressStatus += 1;
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            progBar.setProgress(mProgressStatus);



                            //System.out.println(aga.value);
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
