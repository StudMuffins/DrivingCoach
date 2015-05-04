package com.studmuffins.application;

/**
 * Created by hari on 01/05/15.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class FuelUI extends View {

    private Paint paint;

    private Rect rectECO = new Rect();
    private Rect rectFUEL = new Rect();
    private float signal;
    private String textType;
    private int clipHeight;
    private int yC;

    public FuelUI(Context context) {
        super(context);
    }

    public FuelUI(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setClipping(float progress) {
        paint = new Paint();
        //int convertText = (int)getText;
        //textType = Integer.toString(convertText);

        //apply ANTI_ALIAS for smoothing out the edges of the shapes
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        signal = progress;

        clipHeight = (int) (progress * yC) / 100;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Clip the canvas

        //get the width and height of the canvas
        float width = getWidth();
        float height = getHeight();
        int xC = (int) width;
        yC = (int) height;

        //establish the coordinates and size of each rectangle (Left(x), Top(y), Right(x), Bottom(y));
        rectECO.set(xC - 40, 0, xC, yC);
        rectFUEL.set(xC - 40, yC - clipHeight, xC, yC);
        //Colour and draw the clip arcs of each rectangle
        paint.setColor(Color.parseColor("#64DD17"));
        paint.setShadowLayer(2.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
        canvas.drawRect(rectECO, paint);

        paint.setColor(Color.parseColor("#424242"));
        canvas.drawRect(rectFUEL, paint);

        //colour and draw the gear value text
        paint.clearShadowLayer();
        paint.setColor(Color.parseColor("#000000"));
        paint.setAlpha(208);
        paint.setTextSize(400);
        //canvas.drawText(textType, xC - 125, yC + 150, paint);
    }
}