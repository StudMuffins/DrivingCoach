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
    public Float print1;
    public Float print2;
    public Float print3;
    public Float print4;
    private float velocity;
    private float velocity1;       // m/s
    private float velocity2;       // m/s
    private float velocity3;       // m/s
    private float tempVelo1;
    private float tempVelo2;
    private boolean contstant;
    private boolean decelDone;
    private float tempVelocity;    // m/s
    private long startTime;         // seconds^-9
    private float elapsedTime;       // seconds
    private float deceleration;    // m/s^2
    private float jerk;              // m/s^3
    private AGASystem aga = new AGASystem();
    private TextView text;
    private Handler mHandler = new Handler();
    private float initVelocity;
    private float currentVelocity;
    private float finalVelocity;
    private float calc;
    private float storeFinal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.brake_fragment, container, false);
        //text = (TextView) view.findViewById(R.id.Value);
        //text.setText("5");

        autoListener();
        return view;
    }


    public void autoListener(){

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    //brake = aga.map.get(AutomotiveSignalId.FMS_BRAKE_SWITCH);


                    print = aga.map.get(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED);

                    if (print != null) {
                        velocity = (float) (print / 3.6);
                        //System.out.println("PING!-1");
                    }

                    currentVelocity = velocity;

                    if (currentVelocity > finalVelocity) {
                        initVelocity = currentVelocity;
                        System.out.println("ACCELERATING!");
                    }else if (currentVelocity < initVelocity) {
                        elapsedTime = (long) Math.pow((System.nanoTime() - startTime), 9);
                        finalVelocity = currentVelocity;
                        System.out.println("DECELERATING!");
                    }



                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    /*
                    if (velocity1 < velocity) {

                        //startTime = System.nanoTime();

                        float hari = 0;

                        do {

                            print2 = aga.map.get(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED);
                            if (print2 != null)
                                velocity2 = (float) (print1 / 3.6);

                            if(hari == velocity2){
                                decelDone = true;
                            }

                            for(int i = 0; i == 10; i++){
                                System.out.println(i);
                            }

                            hari = velocity2;
                        } while (velocity2 != 0 && decelDone != true);

                    }

                    elapsedTime = (long) Math.pow((System.nanoTime() - startTime), 9);

                    System.out.println("Velocity  = " + velocity);
                    System.out.println("Velocity1 = " + velocity1);
                    System.out.println("Velocity2 = " + velocity2);


                    //System.out.println("Time taken = " + elapsedTime);

                    */
                    mHandler.post(new Runnable() {
                        public void run() {

                            //text.setText(String.valueOf(jerk));
                        }
                    });
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
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}