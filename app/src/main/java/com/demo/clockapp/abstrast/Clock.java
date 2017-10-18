package com.demo.clockapp.abstrast;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

public abstract class Clock extends View{

    protected int radius;
    protected Point center;
    private Canvas canvas;
    protected Paint paint;

    private int edgeWidth;
    private int middleEdgeWidth;
    private int centerRadius;

    private int[] numbers;


    public int getFontSize() {
        return radius / 10;
    }

    public int getMiddleRadius() {
        return radius - edgeWidth;
    }

    public int getFaceRadius() {
        return radius - edgeWidth - middleEdgeWidth;
    }

    public double getUnitAngle () {
        return Math.PI * 2 / numbers.length;
    }

    public double getAdditionalNumber() {
        return Math.PI / 2 / getUnitAngle();
    }

    public Clock(Context context) {
        super(context);
    }

    public int getNumberDelta() {
        return numbers[1] - numbers[0];
    }

    public void init() {
        edgeWidth = 10;
        middleEdgeWidth = 10;
        centerRadius = 12;
    }

    public void drawEdge() {
        paint.setStrokeWidth(edgeWidth);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(center.x, center.y, radius, paint);

        paint.reset();
    }

    public void drawMiddleEdge() {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(middleEdgeWidth);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(center.x, center.y, getMiddleRadius(), paint);

        paint.reset();
    }

    public void drawFace() {
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(center.x, center.y, getFaceRadius(), paint);

        paint.reset();
    }

    public void drawCenter() {
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(center.x, center.y, centerRadius, paint);

        paint.reset();
    }

    public void drawNumberal() {
        int faceRadius = getFaceRadius();
        int textX, textY;
        double angle;
        Rect rect = new Rect();

        paint.setColor(Color.WHITE);
        paint.setTextSize(getFontSize());

        for (int index = 0; index < numbers.length; index++) {
            String temp = String.valueOf(numbers[index]);
            paint.getTextBounds(temp, 0, temp.length(), rect);

            angle = getUnitAngle() * (index + getAdditionalNumber());

            textX = (int) (center.x + Math.cos(angle) * faceRadius - rect.width() / 2);
            textY = (int) (center.y + Math.sin(angle) * faceRadius + rect.height() / 2);

            canvas.drawText(temp, textX, textY, paint);
        }
        paint.reset();
    }

    public void drawStreak() {
        int strokeStreakWidth = 2;
        int faceRadius = getFaceRadius();
        int startX, startY, endX, endY;

        paint.setStrokeWidth(strokeStreakWidth);

        for (int index = 0; index < numbers.length; index++) {
            double angle = getUnitAngle() * (index + getAdditionalNumber());
            startX = (int) (center.x + Math.cos(angle) * faceRadius);
            startY = (int) (center.y + Math.sin(angle) * faceRadius);
            endX = (int) (startX + Math.cos(angle) * edgeWidth);
            endY = (int) (startY + Math.sin(angle) * edgeWidth);
            canvas.drawLine(startX, startY, endX, endY, paint);
        }
        paint.reset();
    }

    public void drawdrawMiddleStreak() {
        int paddingNumber = 8;
        int strokeStreakWidth = 1;
        int currentRadius = radius + paddingNumber;
        int shortStreakWidth = edgeWidth * 2 / 3;
        double haftAngle = getUnitAngle() / 2;
        double unitAngle = getUnitAngle();
        double deltaNumber = getAdditionalNumber();
        int startX, startY, endX, endY;

        paint.reset();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(strokeStreakWidth);

        for (int index = 0; index < numbers.length; index++) {
            double angle = unitAngle * (index + deltaNumber) + haftAngle;
            startX = (int) (center.x + Math.cos(angle) * currentRadius);
            startY = (int) (center.y / 2 + Math.sin(angle) * currentRadius);
            endX = (int) (startX + Math.cos(angle) * shortStreakWidth);
            endY = (int) (startY + Math.sin(angle) * shortStreakWidth);
            canvas.drawLine(startX, startY, endX, endY, paint);
        }

        paint.reset();
    }

    public void drawdrawHand(double number) {
        double angle = (number / getNumberDelta() + getAdditionalNumber()) * getUnitAngle();
        int handTruncation = edgeWidth * 2;
        int handRadius = radius - handTruncation;

        paint.reset();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(10);

        canvas.drawLine(
                (float) (center.x + Math.cos(angle) * centerRadius),
                (float) (center.y + Math.sin(angle) * centerRadius),
                (float) (center.x + Math.cos(angle) * handRadius),
                (float) (center.y + Math.sin(angle) * handRadius),
                paint
        );

        paint.reset();

        drawRedPoint(angle);
    }

    public void drawRedPoint(double angle) {
        paint.setColor(Color.RED);
        paint.setStrokeWidth(7);

        canvas.drawPoint((float) (center.x + Math.cos(angle) * radius),
                (float) (center.y + Math.sin(angle) * radius), paint);

        paint.reset();
    }

    public void drawGreenPoint(Canvas canvas, double number) {
        paint.reset();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10);

        int greenPoinrRadius = radius/4;
        double angle = (number / getNumberDelta() + getAdditionalNumber()) * getUnitAngle();
        canvas.drawPoint((float) (center.x + Math.cos(angle) * greenPoinrRadius),
                (float) (center.x + Math.sin(angle) * greenPoinrRadius), paint);

        paint.reset();
    }
}
