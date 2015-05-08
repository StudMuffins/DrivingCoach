package com.studmuffins.application;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import android.animation.ValueAnimator;
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
    private Bitmap arrow_1;
    private Bitmap arrow_2;
    private int holderV;
    private int redV;
    private int greenV;
    private float angle_A;
    private float angle_B;
    private float signal;
    private String textType;
    private Context mContext;

    public GearUI(Context context) {
        super(context);
        initVector(context);
    }

    public GearUI(Context context, AttributeSet attrs) {
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
        paint.setTextSize(400);

        arcDial = new RectF();
        arcOrange = new RectF();
        arcSignal = new RectF();

        //arrow_2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_2);
    }

    public void setClipping(float progress, float getText) {
        //arrow_1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.arrow);
        int convertText = (int)getText;
        signal = progress;

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
        } else {
            textType = Integer.toString(convertText);
        }

        //calculate the angle of percentage
        angle_A = (progress * 260) / 100;
        angle_B = 0;


        //run animation for the green arcSignal
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

        //run animation for the red arcSignal
        if (progress >= 75) {
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

    void animateArc() {
        //count from start value to end values with a decelerating speed of a set duration
        ValueAnimator up = ValueAnimator.ofInt(0, 400);
        up.setDuration(300);
        up.setInterpolator(new DecelerateInterpolator());
        up.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                holderV = (Integer) valueAnimator.getAnimatedValue();
            }
        });

        //count from start value to end values with a accelerating speed of a set duration
        ValueAnimator down = ValueAnimator.ofInt(400, 0);
        down.setDuration(300);
        down.setInterpolator(new AccelerateInterpolator());
        down.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                holderV = (Integer) valueAnimator.getAnimatedValue();
            }
        });

        //start animation at a certain percent
        if (greenV == 0 && signal >= 20 && signal <= 30 || redV == 0 && signal >= 85) {
            up.start();
        }
        if (greenV == 400 && signal > 30 || greenV == 400 && signal < 20 || redV == 400 && signal <= 85) {
            down.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Clip the canvas

        //get the width and height of the canvas
        float width = getWidth();
        float height = getHeight();
        float xC = width / 2;
        float yC = height / 2;

        //establish the coordinates and size of each rectangle (Left(x), Top(y), Right(x), Bottom(y));
        arcOrange.set(xC - 360, yC - 360, xC + 360, yC + 360);
        arcSignal.set(xC - holderV, yC - holderV, xC + holderV, yC + holderV);
        arcDial.set(xC - 320, yC - 320, xC + 320, yC + 320);

        //Colour and draw the clip arcs of each rectangle
        if (signal > 0) {
            paint.setColor(Color.parseColor("#FF5722"));
            paint.setShadowLayer(2.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
            canvas.drawArc(arcOrange, 140, angle_A, true, paint);
        }

        //canvas.drawBitmap(arrow_1, xC - 320, yC - 320, null);
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

        if (signal >= 85) {
            paint.clearShadowLayer();
            paint.setColor(Color.parseColor("#000000"));
            paint.setAlpha(100);
            canvas.drawArc(arcSignal, 221 + 140, 39, true, paint);
            paint.setAlpha(255);
            paint.setColor(Color.parseColor("#FF1744"));
            paint.setShadowLayer(8.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
            canvas.drawArc(arcSignal, 221 + 140, angle_B, true, paint);
        }

        //colour the count down signals
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
        //canvas.drawBitmap(arrow_1, xC, yC, null);

        float dialW = arcDial.width()/2;
        float dialH = arcDial.height()/2;

        //colour and draw the gear value text
        paint.clearShadowLayer();
        paint.setColor(Color.parseColor("#000000"));
        paint.setAlpha(208);
        canvas.drawText(textType, dialW + 165, dialH + 540, paint);
    }
}
