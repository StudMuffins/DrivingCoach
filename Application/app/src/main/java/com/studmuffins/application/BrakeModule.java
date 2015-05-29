package com.studmuffins.application;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by oscarbergstrom on 03/05/15.
 *
 * The breaking module is calculating the jerk (m/s^3) to find out how slow a deceleration is.
 * The lower the deceleration is the safer the driver is decelerating. Is the driver pedal-
 * braking, the score (jerk) gets higher.
 */


public class BrakeModule extends Fragment {

    // Introducing variables

    public Float brake;
    private float brakeValue;
    public Float print;                         // km/h
    private float velocity;                     // Current velocity from aga (m/s)
    private long startTime;                     // Time-print from when deceleration starts (ns)
    private long stopTime;                      // Time-print from when deceleration stops (ns)
    private float elapsedTime;                  // Elapsed time during deceleration (ns)
    private float seconds;                      // seconds
    private float deceleration;                 // Average deceleration value (m/s^2)
    private float jerk;                         // Jerk calculated (m/s^3)
    private AGASystem aga = new AGASystem();
    private Handler mHandler = new Handler();
    private float initVelocity;                 // initial velocity when deceleration starts (m/s)
    private float currentVelocity;              // current velocity of vehicle (m/s)
    private float finalVelocity;                // final velocity after a deceleration (m/s)
    private float initProgress;
    private float finalProgress;
    private int res;
    private BrakeUI ui;
    private float totalJerk = 0;                    // All of the jerk values added together.
    private double safeJerks = 0;                   // All of the safe jerks added together.
    private double totalAmountOfJerks = 0;
    private double score = 0;

    double TOTALAMOUNTOFJERKS;
    double SAFEJERKS;
    double SCORE;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.brake_fragment, container, false);
        ui = (BrakeUI) view.findViewById(R.id.UI);
        ui.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        initVar();

        autoListener();
        return view;

    }

    @Override
    public void onStop() {
        super.onStop();  // Always call the superclass method first
        TOTALAMOUNTOFJERKS = totalAmountOfJerks;
        SAFEJERKS = safeJerks;
        SCORE = setTotalScore();
    }

    private void initVar() {
        finalVelocity = 0;
        deceleration = 0;
    }


    public void autoListener() {

        //start thread

        new Thread(new Runnable() {
            public void run() {
                while (true) {

                    print = aga.map.get(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED);  //get value from aga

                    if (print != null) {                                            //convert to float
                        velocity = (float) (print / 3.6);
                    }

                    currentVelocity = velocity;               //set converted value to currentVelocity

                    // Check if car has stopped

                    if (currentVelocity > finalVelocity || currentVelocity < 1) {
                        check();


                    // If car starts decelerating

                    } else if (currentVelocity < initVelocity) {
                        if (startTime == 0) {                                   //if timer not started:
                            startTime = System.nanoTime();                      //start timing
                        }
                        finalVelocity = currentVelocity;
                        finalProgress = (finalVelocity/83) * 100;
                    //    System.out.println("DECELERATING!");
                    }

                    initProgress = (initVelocity/83) * 100;
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

            stopTime = System.nanoTime();                           //stop the timer
            elapsedTime = stopTime - startTime;                     //Calculate the elapsed time
            seconds = elapsedTime / 1000000000;                     //Convert nanoseconds to seconds
            deceleration = (initVelocity - finalVelocity) / seconds;//Calculate deceleration

            //System.out.println("Elapsed time: " + seconds + "sec");

            //System.out.println("Initial velocity: " + initVelocity);
            //System.out.println("Final velocity: " + finalVelocity);
           // System.out.println("Deceleration value: " + deceleration);

            brake = aga.map.get(AutomotiveSignalId.FMS_BRAKE_SWITCH);   //get value from AGA

            if (brake != null) {                                        //convert to float
                brakeValue = brake;
            }

            if (brakeValue == 0) {                                      //check if brake is applied
                calculateBrake();
            } else {
                calculateEngineBrake();
            }

        } else if (deceleration == 0) {
            res = 1;
            reset(res);
        }
    }

    // Calculating the jerks, adding 50% (*1.5) if brake pedal is applied.

    private void calculateBrake() {

        jerk = (float) ((deceleration / seconds) * 1.5);
        //System.out.println("Pedal brake = " + jerk);
        //System.out.println("_____________________________");
        addJerk(jerk);
        res = 0;
        reset(res);
    }

    private void calculateEngineBrake() {

        jerk = (deceleration / seconds);
       // System.out.println("Engine brake = " + jerk);
        //System.out.println("_____________________________");
        addJerk(jerk);                                          //send jerk to addJerk;
        res = 0;
        reset(res);
    }

    // Reset the values before looping again.

    private void reset(int res) {

        finalVelocity = currentVelocity;
        startTime = 0;
        jerk = 0;
        //System.out.println("ACCELERATING!");

        if (res == 0) {
         //   System.out.println("case0");
            deceleration = 0;
            finalVelocity = 0;

        } else if (res == 1) {
        //    System.out.println("case1");
            initVelocity = currentVelocity;
        }
    }

    private void addJerk (float t){
        if(t < 2.0){
            safeJerks = safeJerks + 1;
            System.out.println("safeJerks1: "+ safeJerks);
        }
        totalAmountOfJerks = totalAmountOfJerks + 1;

        System.out.println("totalAmountOfJerks: "+ totalAmountOfJerks);

        System.out.println("Here1");

        totalJerk += t;

    }


    public double setTotalScore(){
        score = 100 * (SAFEJERKS / TOTALAMOUNTOFJERKS);
        System.out.println("safe: "+ SAFEJERKS);
        System.out.println("total: "+ TOTALAMOUNTOFJERKS);
        System.out.println("You got a total score of: " + score + "%");

                return score;
    }

    public double getScore(){
        return SCORE;
    }

}
