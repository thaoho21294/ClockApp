package com.demo.clockapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;

import com.demo.clockapp.abstrast.ClockView;

public class AccelClockView extends ClockView {

    public AccelClockView(Context context) {
        super(context);
    }
    public AccelClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void initClock(Canvas _canvas) {
        double[] numbers = {0, 0.2, 0.4, 0.6, 0.8, 1, -1, -0.8, -0.6, -0.4, -0.2};

        isInit = true;
        init();
        canvas = _canvas;
        radius = 80;
        center = new Point(290, 90);
        currentNumber = 0;
        goodNumber = -0.5;
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
        String title = "Accel";
        String unit = "g";
        int titleFontSize = 12;
        int numberFontSize = 14;
        int unitFontSize = 12;
        int padding = 3;

        paint.setColor(Color.LTGRAY);
        paint.setTextSize(titleFontSize);
        canvas.drawText(title, center.x - 16, center.y - getFaceRadius() / 2 + padding, paint);

        paint.setColor(Color.YELLOW);
        paint.setTextSize(numberFontSize);
        canvas.drawText(Double.toString(currentNumber), center.x - 10, center.y - getFaceRadius() / 2 + numberFontSize + padding, paint);

        paint.setColor(Color.LTGRAY);
        paint.setTextSize(unitFontSize);
        canvas.drawText(unit, center.x - 10, center.y + getFaceRadius() / 2, paint);
    }

}
