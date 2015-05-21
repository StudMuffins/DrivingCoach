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
    private float brakeValue;
    public Float print;
    private float velocity;
    private long startTime;         // nanoseconds
    private long stopTime;          // nanoseconds
    private float elapsedTime;       // nanoseconds
    private float seconds;          //seconds
    private float deceleration;    // m/s^2
    private float jerk;              // m/s^3
    private AGASystem aga = new AGASystem();
    private TextView text;
    private Handler mHandler = new Handler();
    private float initVelocity;
    private float currentVelocity;
    private float finalVelocity;
    private int res;
    private BrakeUI ui;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.brake_fragment, container, false);

        ui = (BrakeUI) view.findViewById(R.id.UI);
        ui.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        initVar();

        autoListener();
        return view;
    }

    private void initVar() {
        finalVelocity = 0;
        deceleration = 0;
    }


    public void autoListener() {

        new Thread(new Runnable() {
            public void run() {
                while (true) {


                    print = aga.map.get(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED);

                    if (print != null) {
                        velocity = (float) (print / 3.6);
                    }

                    currentVelocity = velocity;
                    float initProgress = initVelocity/300 * 100;
                    float finalProgress = finalVelocity/300 * 100;
                    if (currentVelocity > finalVelocity || currentVelocity < 1) {
                        check();

                    } else if (currentVelocity < initVelocity) {
                        if (startTime == 0) {
                            startTime = System.nanoTime();
                        }
                        finalVelocity = currentVelocity;

                        System.out.println("DECELERATING!");
                    }
                    ui.setClipping(initProgress, finalProgress);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    mHandler.post(new Runnable() {
                        public void run() {

                        }
                    });
                }

            }
        }).start();

    }

    private void check() {
        if (finalVelocity < initVelocity && initVelocity > 0 && startTime > 0) {

            stopTime = System.nanoTime();
            elapsedTime = stopTime - startTime;
            seconds = elapsedTime / 1000000000;
            deceleration = (initVelocity - finalVelocity) / seconds;

          //  System.out.println("Elapsed time: " + seconds + "sec");

            //System.out.println("Initial velocity: " + initVelocity);
           // System.out.println("Final velocity: " + finalVelocity);
            // System.out.println("Deceleration value: " + deceleration);

            brake = aga.map.get(AutomotiveSignalId.FMS_BRAKE_SWITCH);
            if (brake != null) {
                brakeValue = brake;
            }

            if (brakeValue == 0) {
                calculateBrake();
            } else {
                calculateEngineBrake();
            }


        } else if (deceleration == 0) {
            res = 1;
            reset(res);
        }
    }


    private void calculateBrake() {

        jerk = (float) ((deceleration / seconds) * 0.5);
        System.out.println("Pedal brake = " + jerk);
        System.out.println("_____________________________");
        res = 0;
        reset(res);
    }

    private void calculateEngineBrake() {

        jerk = (deceleration / seconds);
        System.out.println("Engine brake = " + jerk);
        System.out.println("_____________________________");
        res = 0;
        reset(res);
    }

    private void reset(int res) {

        finalVelocity = currentVelocity;
        startTime = 0;
       // System.out.println("ACCELERATING!");
        if (res == 0) {
            System.out.println("case0");
            deceleration = 0;
        } else if (res == 1) {
            System.out.println("case1");
            initVelocity = currentVelocity;
        }
    }
}