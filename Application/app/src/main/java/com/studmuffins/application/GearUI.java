package com.studmuffins.application;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;


/**
 * Created by hari on 02/05/15.
 */
public class GearUI extends View {

    private Paint paint;
    private RectF arcDial = new RectF();
    private RectF arcSig = new RectF();
    private RectF arcRed = new RectF();
    private RectF arcGreen = new RectF();
    private int redV;
    private int greenV;
    private float angle_A;
    private float angle_B;
    private float signal;
    private String textType;

    public GearUI(Context context) {
        super(context);
    }

    public GearUI(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setClipping(float progress, float getText) {
        paint = new Paint();

        int convertText = (int)getText;

        if(convertText == 251) {
            textType = "P";
        }else
        if (convertText == 0) {
            textType = "N";
        }else
        if(convertText == -1) {
            textType = "R1";
        }else
        if(convertText == -2) {
            textType = "R2";
        }else {
            textType = Integer.toString(convertText);
        }

        //apply ANTI_ALIAS for smoothing out the edges of the shapes
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        signal = progress;

        //calculate the angle of percentage
        angle_A = (progress * 260) / 100;
        angle_B = 0;

        //run animation for the red arc
        if (progress >= 1) {
            animateR();
        }else {
            redV = 0;
        }

        //run animation for the green arc
        if (progress >= 20) {
            angle_B = angle_A - 54;
            animateG();
        }else {
            greenV = 0;
        }

        invalidate();
    }

    void animateR() {
        //count from start value to end values with a decelerating speed of a set duration
        ValueAnimator up = ValueAnimator.ofInt(0, 360);
        up.setDuration(300);
        up.setInterpolator(new DecelerateInterpolator());
        up.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                redV = (Integer) valueAnimator.getAnimatedValue();
            }
        });

        //start animation at a certain percent
        if (redV == 0) {
            up.start();
        }
    }

    void animateG() {
        //count from start value to end values with a decelerating speed of a set duration
        ValueAnimator up = ValueAnimator.ofInt(0, 390);
        up.setDuration(300);
        up.setInterpolator(new DecelerateInterpolator());
        up.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                greenV = (Integer) valueAnimator.getAnimatedValue();
            }
        });

        //count from start value to end values with a accelerating speed of a set duration
        ValueAnimator down = ValueAnimator.ofInt(390, 0);
        down.setDuration(300);
        down.setInterpolator(new AccelerateInterpolator());
        down.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                greenV = (Integer) valueAnimator.getAnimatedValue();
            }
        });

        //start animation at a certain percent
        if (greenV == 0 && signal < 30) {
            up.start();
        }
        if (greenV == 390 && signal > 30) {
            down.start();

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Clip the canvas

        //get the width and height of the canvas
        //float width = getWidth();
        float height = getHeight();
        float xC = 400;
        float yC = height / 2;

        //establish the coordinates and size of each rectangle (Left(x), Top(y), Right(x), Bottom(y));
        arcRed.set(xC - redV, yC - redV, xC + redV, yC + redV);
        arcGreen.set(xC - greenV, yC - greenV, xC + greenV, yC + greenV);
        arcDial.set(xC - 320, yC - 320, xC + 320, yC + 320);
        arcSig.set(xC - 280, yC - 280, xC + 280, yC + 280);

        //Colour and draw the clip arcs of each rectangle
        paint.setColor(Color.parseColor("#FF3D00"));
        paint.setShadowLayer(2.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
        canvas.drawArc(arcRed, 140, angle_A, true, paint);

        paint.setColor(Color.parseColor("#00C853"));
        paint.setShadowLayer(8.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
        canvas.drawArc(arcGreen, 54 + 140, angle_B, true, paint);

        //colour the count down signals
        paint.setColor(Color.parseColor("#F5F5F5"));
        paint.setShadowLayer(16.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
        if (signal >= 16 && signal <= 30) {
            paint.setColor(Color.parseColor("#FFAB00"));
            if (signal >= 18 && signal <= 20) {
                paint.setColor(Color.parseColor("#FFD600"));
            } else
            if (signal >= 20 && signal <= 30) {
                paint.setColor(Color.parseColor("#00C853"));
            }
        }
        canvas.drawArc(arcDial, 0, 360, true, paint);

        float dialW = arcDial.width()/2;
        float dialH = arcDial.height()/2;

        //colour and draw the gear value text
        paint.clearShadowLayer();
        paint.setColor(Color.parseColor("#000000"));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAlpha(208);
        paint.setTextSize(400);
        canvas.drawText(textType, dialW + 80, dialH + 540, paint);
    }
}
