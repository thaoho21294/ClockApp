package com.demo.clockapp;

import android.content.Context;
import android.graphics.Canvas;

import com.demo.clockapp.abstrast.Clock;

public class SpeedClockView extends Clock {
    boolean isInit = false;

    public SpeedClockView(Context context) {
        super(context);
    }
    private void initClock(Canvas canvas) {
        isInit = true;
        setCanvas(canvas);
    }
    @Override
    public void onDraw(Canvas canvas) {
        if(!isInit) {
            initClock(canvas);
        }
        drawEdge();
        drawMiddleEdge();
        drawFace();
        drawCenter();
        drawStreak();
        drawdrawMiddleStreak();
        drawNumeral();
        drawHand(171.8);
        drawGreenPoint(160);
        postInvalidateDelayed(500);
    }
}