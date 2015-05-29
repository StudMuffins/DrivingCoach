package com.studmuffins.application;

/**
 * Created by hari on 01/05/15.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class FuelUI extends View {

    private Paint paint;
    private RectF rectECO;
    private RectF rectFUEL;
    private RectF rectClip;
    private String textType;
    private float xC;
    private float angle;


    public FuelUI(Context context) {
        super(context);
    }

    public FuelUI(Context context, AttributeSet attrs) {
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
        // Retrieve the display metrics of the current phone in use
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        xC = displayMetrics.widthPixels/2;

        rectECO = new RectF();
        rectFUEL = new RectF();
        rectClip = new RectF();

    }

    public void setClipping(float progress, float getText) {

        int convertText = (int) getText;
        textType = Integer.toString(convertText);

        angle = (progress * 360)/100;

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Clip the canvas

        // Get the width and height of the canvas
        float width = getWidth();
        float height = getHeight();
        float yC = getHeight()/2 + 450; // -290
        float dL = xC - xC + 300;
        float dT = yC - xC + 300;
        float dR = xC + xC - 300;
        float dD = yC + xC - 300;

        // Establish the coordinates and size of each rectangle (Left(x), Top(y), Right(x), Bottom(y));
        rectFUEL.set(dL, dT, dR, dD);
        rectECO.set(dL + 10, dT + 10, dR - 10, dD - 10);
        rectClip.set(0, dT - 25, width, height);

        // Colour and draw the clip arcs of each rectangle
        paint.setShadowLayer(16.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
        paint.setColor(Color.parseColor("#F5F5F5"));
        canvas.drawRect(rectClip, paint);
        paint.clearShadowLayer();
        paint.setColor(Color.parseColor("#000000"));
        paint.setAlpha(50);
        canvas.drawArc(rectFUEL, 0, 360, true, paint);
        paint.setColor(Color.parseColor("#00C853"));
        canvas.drawArc(rectFUEL, 90, angle, true, paint);

        paint.setColor(Color.parseColor("#FAFAFA"));
        canvas.drawArc(rectECO, 0, 360, true, paint);

        // Colour and draw the gear value text
        paint.setColor(Color.parseColor("#000000"));
        paint.setAlpha(222);
        paint.setTextSize(70);
        canvas.drawText(textType, xC, yC + 20, paint);
        paint.setAlpha(138);
        paint.setTextSize(30);
        canvas.drawText("km/l", xC, yC + 50, paint);
    }
}