package com.studmuffins.application;

/**
 * Created by hari on 01/05/15.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

public class FuelUI extends View {

    private Paint paint;
    private Paint mFillPaint;
    private Path maskArc;
    private RectF rectECO = new RectF();
    private RectF rectFUEL = new RectF();
    private RectF rectClip = new RectF();
    private float signal;
    private String textType;
    private int clipHeight;
    private float xC;
    private float yC;
    private Path mClippingPath;

    public FuelUI(Context context) {
        super(context);
    }

    public FuelUI(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVector();
    }

    public void initVector() {
        mClippingPath = new Path();
    }

    public void setClipping(float progress) {
        paint = new Paint();
        mFillPaint = new Paint();
        //int convertText = (int)getText;
        //textType = Integer.toString(convertText);

        //apply ANTI_ALIAS for smoothing out the edges of the shapes
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        //paint.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.FILL);
        signal = progress;
        mClippingPath.reset();
        clipHeight = (int) progress;

        mClippingPath.moveTo(rectClip.centerX(), rectClip.centerY());
        //Draw an arc from center to given angle
        mClippingPath.addArc(rectClip, 0, 360);
        //Draw a line from end of arc to center
        mClippingPath.lineTo(rectClip.centerX(), rectClip.centerY());
        //Redraw the canvas
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Clip the canvas

        //get the width and height of the canvas
        float width = getWidth();
        float height = getHeight();
        xC = width / 2;
        yC = height / 2 + 200;

        //establish the coordinates and size of each rectangle (Left(x), Top(y), Right(x), Bottom(y));
        //rectECO.set(xC - 500, yC - 500, xC + 500, yC + 500);
        rectECO = new RectF(xC - 450, yC - 450, xC + 450, yC + 450);
        rectFUEL = new RectF(xC - 450, yC - 450, xC + 450, yC + 450);
        rectClip = new RectF(xC - 400, yC - 400, xC + 400, yC + 400);
        //Colour and draw the clip arcs of each rectangle

        canvas.clipPath(mClippingPath, Region.Op.DIFFERENCE);
        paint.setColor(Color.parseColor("#00C853"));
        paint.setShadowLayer(2.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
        canvas.drawArc(rectECO, 40, 100, true, paint);
        paint.setColor(Color.parseColor("#424242"));
        paint.clearShadowLayer();
        canvas.drawArc(rectFUEL, 140, -clipHeight, true, paint);
        //canvas.drawArc(rectECO, 20, 130, true, paint);
        //colour and draw the gear value text
    }
}