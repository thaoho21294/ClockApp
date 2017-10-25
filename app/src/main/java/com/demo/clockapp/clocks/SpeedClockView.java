package com.demo.clockapp.clocks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;

import com.demo.clockapp.abstrast.ClockView;

public class SpeedClockView extends ClockView {

    public SpeedClockView(Context context) {
        super(context);
    }

    public SpeedClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void initClock(Canvas _canvas) {
        double[] numbers = {0, 24, 47, 71, 95, 118, 142, 166, 189};

        isInit = true;
        init();
        canvas = _canvas;
        radius = 80;
        center = new Point(90, 90);
        currentNumber = 172;
        goodNumber = 160;
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
        String title = "Speed";
        String unit = "km/h";
        int titleFontSize = 12;
        int numberFontSize = 14;
        int unitFontSize = 12;
        int padding = 3;

        paint.setColor(Color.LTGRAY);
        paint.setTextSize(titleFontSize);
        canvas.drawText(title, center.x - 16, center.y - getFaceRadius() / 2 + padding, paint);

        paint.setColor(Color.YELLOW);
        paint.setTextSize(numberFontSize);
        canvas.drawText(Double.toString(currentNumber), center.x - 16, center.y - getFaceRadius() / 2 + numberFontSize + padding, paint);

        paint.setColor(Color.LTGRAY);
        paint.setTextSize(unitFontSize);
        canvas.drawText(unit, center.x - 14, center.y + getFaceRadius() / 2, paint);
    }
}