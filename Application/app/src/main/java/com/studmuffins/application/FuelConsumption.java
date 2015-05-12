package com.studmuffins.application;

/**
 * Created by hari on 08/04/15.
 */
//Hi zoe!
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

public class FuelConsumption extends Fragment {
    private FuelUI ui;
    private TextView text;
    private Handler mHandler = new Handler();
    private AGASystem aga = new AGASystem();
    private Float signal;
    private float sendSignal;
    private float textSignal;
    private int progress;
    private ProgressBar bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fuel_fragment, container, false);
        ui = (FuelUI) view.findViewById(R.id.UI);
        bar = (ProgressBar) view.findViewById(R.id.progressBar);
        //ui.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        text = (TextView) view.findViewById(R.id.Value);
        dosomething();
        return view;
    }

    public void dosomething() {

        new Thread(new Runnable() {
            public void run() {
                final int presentage = 0;
                while (true) {
                    signal = aga.map.get(AutomotiveSignalId.FMS_INSTANTANEOUS_FUEL_ECONOMY);

                    if (signal != null) {
                        sendSignal = signal;
                    }
                    //float convertSignal = sendSignal * 100;
                   // if (convertSignal >= 10) {
                        //convertSignal = 10;

                    //}
                    progress = (int) (sendSignal/125) * 100;
                    //Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            bar.setProgress(progress);
                            //ui.setClipping(progress);
                            text.setText(""+progress+"%");
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
