package com.studmuffins.application;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by oscarbergstrom on 03/05/15.
 */


public class BrakeModule extends Fragment {

    public Float brake;
    public Float print;
    private float velocity;
    private float velocity1;       // m/s
    private float velocity2;       // m/s
    private long startTime;         // seconds^-9
    private float elapsedTime;       // seconds
    private float deceleration;    // m/s^2
    private float jerk;              // m/s^3
    private AGASystem aga = new AGASystem();
    private TextView text;
    private Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.brake_fragment, container, false);
        text = (TextView) view.findViewById(R.id.Value);
        text.setText("5");

        autoListener();
        return view;
    }


    public void autoListener(){

        new Thread(new Runnable() {
            public void run() {
                while (true) {

                    print = aga.map.get(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED);
                    brake = aga.map.get(AutomotiveSignalId.FMS_BRAKE_SWITCH);

                    if (print != null) {
                        velocity = print;  // velocity = print/(float)3.6;
                    }
                    //System.out.println("PING 1");

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }



                    while (velocity >= 2.5) {

                        do {
                            velocity1 = velocity;
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } while (velocity1 <= velocity);

                        startTime = System.nanoTime();

                        if (brake == 1) {

                            do {
                                velocity2 = velocity;
                                try {
                                    Thread.sleep(200);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } while (velocity2 >= velocity);

                            elapsedTime = (long) Math.pow((System.nanoTime() - startTime), 9);

                            calculateBrake();
                        }

                        if (brake == 0) {

                            do {
                                velocity2 = velocity;
                                sleep();
                            } while (velocity2 >= velocity);

                            elapsedTime = (long) Math.pow((System.nanoTime() - startTime), 9);

                            calculateEngineBrake();
                        }

                        mHandler.post(new Runnable() {
                            public void run() {

                                text.setText(String.valueOf(jerk));
                            }
                        });
                    }


                }

            }
        }).start();

   }





    private void calculateBrake(){
        deceleration = (velocity1 - velocity2) / elapsedTime;

        jerk = (float) ((deceleration/elapsedTime) * 0.5);

        System.out.print("Pedal brake = " + jerk);
    }

    private void calculateEngineBrake(){
        deceleration = (velocity1 - velocity2) / elapsedTime;

        jerk = (long) (deceleration/elapsedTime);

        System.out.print("Engine brake = " + jerk);
    }



    private void sleep(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

