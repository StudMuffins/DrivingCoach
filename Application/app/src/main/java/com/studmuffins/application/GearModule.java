package com.studmuffins.application;

/**
 * Created by hari on 23/03/15.
 */

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;
import android.widget.TextView;
import android.swedspot.automotiveapi.AutomotiveSignalId;

public class GearModule extends Fragment {

    private GearUI ui;
    private TextView text;
    private Handler mHandler = new Handler();
    private Float print;
    private float signal;
    private float progress;
    private AGASystem aga = new AGASystem();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gear_fragment, container, false);
        ui = (GearUI) view.findViewById(R.id.UI);
        ui.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        text = (TextView) view.findViewById(R.id.RPM);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"digital-7.ttf");
        text.setTypeface(font);

        autoListener();
        return view;
    }

    public void autoListener() {

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    print = aga.map.get(AutomotiveSignalId.FMS_ENGINE_SPEED);


                    if(print != null) {
                        signal = print.floatValue();
                    }

                    progress = (signal / 10000) * 100;

                    mHandler.post(new Runnable() {
                        public void run() {
                            ui.setClipping(progress);
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
