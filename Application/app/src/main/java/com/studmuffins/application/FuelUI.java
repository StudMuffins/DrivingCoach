package com.studmuffins.application;

/**
 * Created by hari on 01/05/15.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class FuelUI extends View {

    private Paint paint;
    private RectF arcDial = new RectF();
    private RectF arcDial2 = new RectF();
    private Rect rectECO = new Rect();
    private Rect rectFUEL = new Rect();
    private float angle_A;
    private float angle_B;

    public FuelUI(Context context) {
        super(context);
    }

    public FuelUI(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*public void setClipping(float progress) {
        paint = new Paint();
        angle_A = (progress * 260) / 100;
        angle_B = 0;
        invalidate();
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        //Clip the canvas
        int width = getWidth();
        int height = getHeight();
        float xC = width/2;
        float yC = height/2;
        final int xR = 40 - 25/2;

        rectECO.set( xR, 40, xR + 25, height - 40);

        rectFUEL.set( xR, 500, xR + 25, height - 40);
        arcDial.set( 0, 0, 80, 80);
        arcDial2.set( 0, height - 80, 80, height);

        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#64DD17"));
       // paint.setShadowLayer(10, 0, 0, Color.BLACK);
        canvas.drawRect(rectECO, paint);
        //paint.clearShadowLayer();
        paint.setColor(Color.parseColor("#424242"));
        canvas.drawRect(rectFUEL, paint);

        paint.setColor(Color.parseColor("#64DD17"));
        //paint.setShadowLayer(10, 0, 0, Color.BLACK);
        canvas.drawArc(arcDial, 0, 360, true, paint);
        //paint.clearShadowLayer();
        paint.setColor(Color.parseColor("#424242"));
        //paint.setShadowLayer(10, 0, 0, Color.BLACK);
        canvas.drawArc(arcDial2, 0, 360, true, paint);
        //paint.clearShadowLayer();
    }
}