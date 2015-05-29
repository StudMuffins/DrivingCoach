package com.studmuffins.application;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import android.animation.ValueAnimator;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by hari on 02/05/15.
 */

public class GearUI extends View {

    private Paint paint;
    private RectF arcDial;
    private RectF arcOrange;
    private RectF arcSignal;
    private float xC;
    private int holderV;
    private int redV;
    private int greenV;
    private float angle_A;
    private float angle_B;
    private float signal;
    private String textType;

    public GearUI(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVector(context);
    }

    // Initialise variables
    public void initVector(Context context) {
        // Apply ANTI_ALIAS for smoothing out the edges of the shapes
        paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(500);

        // Retrieve the display metrics of the current phone in use
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        xC = displayMetrics.widthPixels/2;

        arcDial = new RectF();
        arcOrange = new RectF();
        arcSignal = new RectF();
    }

    public void setClipping(float progress, double getText) {
        int convertText = (int)getText;
        signal = progress;

        if(convertText == 251) {
            textType = "P";
        } else
        if (convertText == 0) {
            textType = "N";
        } else
        if(convertText == -1) {
            textType = "R1";
        } else
        if(convertText == -2) {
            textType = "R2";
        } else {
            textType = Integer.toString(convertText);
        }

        // Calculate the angle of percentage
        angle_A = (progress * 260) / 100;
        angle_B = 0;


        // Run animation for the green arcSignal
        if (progress >= 5 && progress < 50) {
            greenV = holderV;
            animateArc();
            if (progress >= 20) {
                angle_B = angle_A - 53;
                if(progress > 30) {
                    angle_B = 24;
                }
            }
        } else {
            greenV = 0;
        }

        // Run animation for the red arcSignal
        if (progress >= 70) {
            redV = holderV;
            animateArc();
            if (progress >= 85) {
                textType = "!!!";
                angle_B = angle_A - 220;
            }
        } else {
            redV = 0;
        }

        //Redraw the canvas
        postInvalidate();
    }

    private void animateArc() {
        // Count from start value to end values with a decelerating speed of a set duration
        int startValue = 0;
        int endValue = 60;

        ValueAnimator up = ValueAnimator.ofInt(startValue, endValue);
        up.setDuration(300);
        up.setInterpolator(new DecelerateInterpolator());
        up.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                holderV = (Integer) valueAnimator.getAnimatedValue();
            }
        });

        // Count from start value to end values with a accelerating speed of a set duration
        ValueAnimator down = ValueAnimator.ofInt(endValue, startValue);
        down.setDuration(300);
        down.setInterpolator(new AccelerateInterpolator());
        down.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                holderV = (Integer) valueAnimator.getAnimatedValue();
            }
        });

        // Start animation at a certain percentage
        if (greenV == startValue && signal >= 20 && signal <= 30 || redV == startValue && signal >= 85) {
            up.start();
        }
        if (greenV == endValue && signal > 30 || greenV == endValue && signal < 20 || redV == endValue && signal <= 85) {
            down.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Clip the canvas

        // Get the width and height of the canvas
        float yC = getHeight()/2 - 220;
        float dL = xC - xC + 90;
        float dT = yC - xC + 90;
        float dR = xC + xC - 90;
        float dD = yC + xC - 90;

        // Establish the coordinates and size of each rectangle (Left(x), Top(y), Right(x), Bottom(y));
        arcOrange.set(dL - 20, dT - 20, dR + 20, dD + 20);
        arcSignal.set(dL - holderV, dT - holderV, dR + holderV, dD + holderV);
        arcDial.set(dL, dT, dR, dD);

        // Colour and draw the clip arcs of each rectangle
        // Current RPM (orange dial)
        if (signal > 0) {
            paint.setColor(Color.parseColor("#FF5722"));
            paint.setShadowLayer(2.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
            canvas.drawArc(arcOrange, 140, angle_A, true, paint);
        }

        // Display the gear change signal in green and the shadow underline
        if (signal >= 10 && signal < 40) {
            paint.clearShadowLayer();
            paint.setColor(Color.parseColor("#000000"));
            paint.setAlpha(100);
            canvas.drawArc(arcSignal, 54 + 140, 24, true, paint);
            paint.setAlpha(255);
            paint.setColor(Color.parseColor("#00C853"));
            paint.setShadowLayer(8.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
            canvas.drawArc(arcSignal, 54 + 140, angle_B, true, paint);
        }

        if (signal >= 70) {
            paint.clearShadowLayer();
            paint.setColor(Color.parseColor("#000000"));
            paint.setAlpha(100);
            canvas.drawArc(arcSignal, 221 + 140, 39, true, paint);
            paint.setAlpha(255);
            paint.setColor(Color.parseColor("#FF1744"));
            paint.setShadowLayer(8.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
            canvas.drawArc(arcSignal, 221 + 140, angle_B, true, paint);
        }

        // Colour the count down signals
        paint.setColor(Color.parseColor("#F5F5F5"));
        paint.setShadowLayer(16.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
        if (signal >= 16 && signal <= 30) {
            paint.setColor(Color.parseColor("#80D8FF"));
            if (signal >= 18 && signal <= 20) {
                paint.setColor(Color.parseColor("#00B0FF"));
            } else
            if (signal >= 20 && signal <= 30) {
                paint.setColor(Color.parseColor("#00C853"));
            }
        }
        if (signal >= 85) {
            paint.setColor(Color.parseColor("#FF1744"));
        }
        canvas.drawArc(arcDial, 0, 360, true, paint);

        // Colour and draw the gear value text
        paint.clearShadowLayer();
        paint.setColor(Color.parseColor("#000000"));
        paint.setAlpha(222);
        canvas.drawText(textType, xC, yC + 180, paint);
    }
}
