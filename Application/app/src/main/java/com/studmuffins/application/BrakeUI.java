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

public class BrakeUI extends View {

    private Paint paint;
    private RectF arcSignal;
    private float xC;
    //private float yC;
    private int holderV;
    private float angle_A;
    private float angle_B;
    private float angle_C;
    private float angle_D;
    private float signal;
    private String textType;
    private Context mContext;

    public BrakeUI(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initVector(context);
    }

    public void initVector(Context context) {
        //apply ANTI_ALIAS for smoothing out the edges of the shapes
        paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        float height = displayMetrics.heightPixels/2;
        xC = displayMetrics.widthPixels/2;

        arcSignal = new RectF();

        //arrow_2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_2);
    }

    public void setClipping(float initProgress, float finalProgress) {
        //arrow_1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.arrow);
        signal = finalProgress;

        //calculate the angle of percentage
        angle_A = (initProgress * 180) / 100;
        angle_B = (finalProgress * 180) / 100;
        //System.out.println("ANGLE_B: " + angle_B);


        //Redraw the canvas
        postInvalidate();
    }

    private void animateArc() {
        //count from start value to end values with a decelerating speed of a set duration
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

        //count from start value to end values with a accelerating speed of a set duration
        ValueAnimator down = ValueAnimator.ofInt(endValue, startValue);
        down.setDuration(300);
        down.setInterpolator(new AccelerateInterpolator());
        down.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                holderV = (Integer) valueAnimator.getAnimatedValue();
            }
        });

        //start animation at a certain percent
        /*
        if (greenV == startValue && signal >= 20 && signal <= 30 || redV == startValue && signal >= 85) {
            up.start();
        }
        if (greenV == endValue && signal > 30 || greenV == endValue && signal < 20 || redV == endValue && signal <= 85) {
            down.start();
        }*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Clip the canvas

        //get the width and height of the canvas
        //System.out.println("xC= " + xC);
        //System.out.println("yC= " + yC);
        float width = getWidth();
        float yC = getHeight()/2 + 340; // -290
        float dL = xC - xC + 200;
        float dT = yC - xC + 200;
        float dR = xC + xC - 200;
        float dD = yC + xC - 200;

        holderV = 0;

        //System.out.println("width= " + width);
        //System.out.println("height= " + yC);

        //establish the coordinates and size of each rectangle (Left(x), Top(y), Right(x), Bottom(y));
        arcSignal.set(dL - holderV, dT - holderV, dR + holderV, dD + holderV);

        //Colour and draw the clip arcs of each rectangle

        //canvas.drawBitmap(arrow_1, xC - 320, yC - 320, null);

        paint.setColor(Color.parseColor("#00C853"));
        paint.setShadowLayer(8.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
        canvas.drawArc(arcSignal, 180, angle_A, true, paint);

        if (signal > 0) {
            paint.setAlpha(255);
            paint.setColor(Color.parseColor("#00B0FF"));
            paint.setShadowLayer(8.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
            canvas.drawArc(arcSignal, 180 + angle_A, - angle_A + angle_B, true, paint);
        }
    }
}

