package com.demo.clockapp.clocks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.demo.clockapp.MainActivity;
import com.demo.clockapp.abstrast.ClockView;



public class RevsClockView extends ClockView {
    public RevsClockView(Context context) {
        super(context);
    }
    public RevsClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void initClock(Canvas _canvas) {
        double[] numbers = {0, 1, 2, 3, 4, 5, 6, 7};

        isInit = true;
        init();
        canvas = _canvas;
        radius = 80;
        center = new Point(90, 270);
        currentNumber = 1.8;
        goodNumber = 0;
        setNumbers(numbers);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if(!isInit) {
            initClock(canvas);
        }
        drawAll();
    }

    public void drawInfo() {
        String title = "Revs";
        int number = 1724;
        String addUnit = "x1000";
        String unit = "rm";
        int titleFontSize = 12;
        int numberFontSize = 28;
        int unitFontSize = 12;
        int padding = 3;

        paint.setColor(Color.LTGRAY);
        paint.setTextSize(titleFontSize);
        canvas.drawText(title, center.x - 16, center.y - getFaceRadius() / 2 + padding, paint);

        paint.setColor(Color.YELLOW);
        paint.setTextSize(numberFontSize);
        canvas.drawText(Integer.toString(number), center.x - 30, center.y - padding, paint);

        paint.setColor(Color.LTGRAY);
        paint.setTextSize(unitFontSize);
        canvas.drawText(addUnit, center.x - 20, center.y + getFaceRadius() / 3, paint);

        paint.setColor(Color.LTGRAY);
        paint.setTextSize(unitFontSize);
        canvas.drawText(unit, center.x - 10, center.y + getFaceRadius() / 2 , paint);
    }

    @Override
    public void drawHand() {
        int handWidth = centerRadius;
        double angle = (currentNumber / getNumberDelta() + getAdditionalNumber()) * getUnitAngle();
        float currentRadius = getFaceRadius() - middleEdgeWidth;

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(handWidth);
        RectF oval = new RectF();
        oval.set(   center.x - currentRadius,
                    center.y - currentRadius ,
                    center.x + currentRadius,
                    center.y + currentRadius
        );
        canvas.drawArc(oval, 90, (float)(angle/Math.PI * 180 - 90), false, paint);

        paint.reset();

        drawRedPoint(angle);
    }

    @Override
    public void drawGreenPoint() {
        int pointWidth = radius/13;
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(pointWidth);

        int greenPointRadius = getFaceRadius() - 2 * middleEdgeWidth;
        double angle = (goodNumber / getNumberDelta() + getAdditionalNumber()) * getUnitAngle();

        canvas.drawPoint((float) (center.x + Math.cos(angle) * greenPointRadius),
                (float) (center.y + Math.sin(angle) * greenPointRadius), paint);

        paint.reset();
    }

    @Override
    public void drawAll() {
        drawEdge();
        drawMiddleEdge();
        drawFace();
        drawStreak();
        drawMiddleStreak();
        drawNumeral();
        drawHand();
        drawGreenPoint();
        drawInfo();
    }
}
