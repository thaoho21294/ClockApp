package com.demo.clockapp.abstrast;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public abstract class ClockView extends View{

    protected int radius;
    protected Point center;
    protected Canvas canvas;
    protected Paint paint;

    protected int edgeWidth;
    protected int middleEdgeWidth;
    protected int centerRadius;
    private double[] numbers;
    protected double currentNumber;
    protected boolean isInit = false;
    protected double goodNumber;

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public int getFontSize() {
        return radius / 6;
    }

    public int getMiddleRadius() {
        return radius - edgeWidth;
    }

    public int getFaceRadius() {
        return getMiddleRadius() - middleEdgeWidth/2;
    }

    public double getUnitAngle () {
        return Math.PI * 2 / numbers.length;
    }

    public double getAdditionalNumber() {
        return Math.PI / 2 / getUnitAngle();
    }

    public double getNumberDelta() {
        return numbers[1] - numbers[0];
    }

    public void setNumbers(double []_number) {
        numbers = _number;
    }

    public void init() {
        edgeWidth = 6;
        middleEdgeWidth = 6;
        centerRadius = 6;
        paint = new Paint();
    }

    public void drawAll() {
        drawEdge();
        drawMiddleEdge();
        drawFace();
        drawCenter();
        drawStreak();
        drawMiddleStreak();
        drawNumeral();
        drawHand();
        drawGreenPoint();
        drawInfo();
    }

    public void drawEdge() {
        LinearGradient linearGradient = new LinearGradient(0, radius * 2, radius * 2, 0,
                new int[]{
                        Color.BLACK,
                        Color.WHITE,
                        Color.BLACK},
                new float[]{0, 0.5f, 1},
                Shader.TileMode.MIRROR);
        paint.setShader(linearGradient);
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
        LinearGradient linearGradient = new LinearGradient(0, getFaceRadius() * 2, 0, 0, Color.BLACK, Color.GRAY, Shader.TileMode.MIRROR);
        paint.setShader(linearGradient);
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

    public void drawNumeral() {
        int faceRadius = getFaceRadius() - middleEdgeWidth - getFontSize();
        int textX, textY;
        double angle;
        Rect rect = new Rect();
        String temp;

        paint.setColor(Color.LTGRAY);
        paint.setTextSize(getFontSize());

        for (int index = 0; index < numbers.length; index++) {
            double number = numbers[index];
            temp = number == (int)number ? String.valueOf((int)number) : String.valueOf(number);
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
        int currentRadius = getFaceRadius() - middleEdgeWidth;
        int startX, startY, endX, endY;

        paint.setStrokeWidth(strokeStreakWidth);
        paint.setColor(Color.LTGRAY);

        for (int index = 0; index < numbers.length; index++) {
            double angle = getUnitAngle() * (index + getAdditionalNumber());
            startX = (int) (center.x + Math.cos(angle) * currentRadius);
            startY = (int) (center.y + Math.sin(angle) * currentRadius);
            endX = (int) (startX + Math.cos(angle) * edgeWidth);
            endY = (int) (startY + Math.sin(angle) * edgeWidth);
            canvas.drawLine(startX, startY, endX, endY, paint);
        }
        paint.reset();
    }

    public void drawMiddleStreak() {
        int strokeStreakWidth = 1;
        int currentRadius = getFaceRadius() - middleEdgeWidth;
        int shortStreakWidth = edgeWidth * 2 / 3;
        double haftAngle = getUnitAngle() / 2;
        double unitAngle = getUnitAngle();
        double deltaNumber = getAdditionalNumber();
        int startX, startY, endX, endY;

        paint.reset();
        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(strokeStreakWidth);

        for (int index = 0; index < numbers.length; index++) {
            double angle = unitAngle * (index + deltaNumber) + haftAngle;
            startX = (int) (center.x + Math.cos(angle) * currentRadius);
            startY = (int) (center.y + Math.sin(angle) * currentRadius);
            endX = (int) (startX + Math.cos(angle) * shortStreakWidth);
            endY = (int) (startY + Math.sin(angle) * shortStreakWidth);
            canvas.drawLine(startX, startY, endX, endY, paint);
        }

        paint.reset();
    }

    public void drawHand() {
        int handWidth = centerRadius;
        double angle = (currentNumber / getNumberDelta() + getAdditionalNumber()) * getUnitAngle();
        int handTruncation = middleEdgeWidth * 3 / 2 + edgeWidth;
        int handRadius = getFaceRadius() - handTruncation;

        paint.reset();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(handWidth);

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
        int pointWidth = radius/13;
        int currentRadius = getFaceRadius() - middleEdgeWidth;
        paint.setColor(Color.RED);
        paint.setStrokeWidth(pointWidth);

        canvas.drawPoint((float) (center.x + Math.cos(angle) * currentRadius),
                (float) (center.y + Math.sin(angle) * currentRadius), paint);

        paint.reset();
    }

    public void drawGreenPoint() {
        int pointWidth = radius/13;
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(pointWidth);

        int greenPointRadius = radius/4;
        double angle = (goodNumber / getNumberDelta() + getAdditionalNumber()) * getUnitAngle();

        canvas.drawPoint((float) (center.x + Math.cos(angle) * greenPointRadius),
                (float) (center.y + Math.sin(angle) * greenPointRadius), paint);

        paint.reset();
    }

    abstract public void drawInfo();
}
