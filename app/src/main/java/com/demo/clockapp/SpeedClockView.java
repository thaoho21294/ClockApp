package com.demo.clockapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class SpeedClockView extends View {

    private int height, width = 0;
    private int padding = 0;
    private int fontSize = 0;
    private int numeralSpacing = 0;
    private int handTruncation = 0;
    private int radius = 0;
    private Paint paint;
    private boolean isInit;
    private int strokeWidth = 10;
    private int streakWidth = 8;
    private int[] numbers = {0, 24, 47, 71, 95, 118, 142, 166, 189};
    //    private int[] numbers = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
//    private int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7};
    private int distance = numbers[1] - numbers[0];
    private int amount = numbers.length;
    private double minAngle = Math.PI * 2 / amount;
    private double deltaNumber = Math.PI / 2 / minAngle;
    private int centerRadius = 12;
    Rect rect = new Rect();

    public SpeedClockView(Context context) {
        super(context);
    }

    public SpeedClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void initClock() {
        height = getHeight();
        width = getWidth();
        padding = numeralSpacing + 50;
        fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics());
        int min = Math.min(height, width);
        radius = min / 2 - padding;
        handTruncation = strokeWidth * 2;
        paint = new Paint();
        isInit = true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (!isInit) {
            initClock();
        }
        canvas.drawColor(Color.BLACK);
        drawCircle(canvas);
        drawMiddleCircle(canvas);
        drawInsideCircle(canvas);
        drawCenter(canvas);
        drawStreak(canvas);
        drawMiddleStreak(canvas);
        drawNumeral(canvas);
        drawHand(canvas, 171.8);
        drawGreenPoint(canvas, 160);
        postInvalidateDelayed(500);
    }

    private void drawCircle(Canvas canvas) {
        int circleRadius = radius + padding - 10;
        LinearGradient linearGradient = new LinearGradient(0, circleRadius * 2, circleRadius * 2, 0,
                new int[]{
                        Color.BLACK,
                        Color.WHITE,
                        Color.BLACK},
                new float[]{0, 0.5f, 1},
                Shader.TileMode.MIRROR);
        paint.reset();
        paint.setShader(linearGradient);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, height / 2, circleRadius, paint);
    }

    private void drawMiddleCircle(Canvas canvas) {
        paint.reset();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, height / 2, radius + padding - 10 - strokeWidth, paint);
    }

    private void drawInsideCircle(Canvas canvas) {
        int circleRadius = radius + padding - 10 - strokeWidth * 2;
        LinearGradient linearGradient = new LinearGradient(0, circleRadius * 2, 0, 0, Color.BLACK, Color.GRAY, Shader.TileMode.MIRROR);
        paint.reset();
        paint.setShader(linearGradient);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, height / 2, circleRadius, paint);
    }

    private void drawCenter(Canvas canvas) {
        paint.reset();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, height / 2, centerRadius, paint);
    }

    private void drawNumeral(Canvas canvas) {
        int currentRadius = radius - strokeWidth;
        paint.reset();
        paint.setColor(Color.WHITE);
        paint.setTextSize(fontSize);
        for (int index = 0; index < amount; index++) {
            String temp = String.valueOf(numbers[index]);
            paint.getTextBounds(temp, 0, temp.length(), rect);
            double angle = minAngle * (index + deltaNumber);
            int textX = (int) (width / 2 + Math.cos(angle) * currentRadius - rect.width() / 2);
            int textY = (int) (height / 2 + Math.sin(angle) * currentRadius + rect.height() / 2);
            canvas.drawText(temp, textX, textY, paint);
        }
    }

    private void drawStreak(Canvas canvas) {
        int paddingNumber = 8;
        int strokeStreakWidth = 2;
        int currentRadius = radius + paddingNumber;
        paint.reset();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(strokeStreakWidth);
        for (int index = 0; index < amount; index++) {
            double angle = minAngle * (index + deltaNumber);
            int startX = (int) (width / 2 + Math.cos(angle) * currentRadius);
            int startY = (int) (height / 2 + Math.sin(angle) * currentRadius);
            int endX = (int) (startX + Math.cos(angle) * streakWidth);
            int endY = (int) (startY + Math.sin(angle) * streakWidth);
            canvas.drawLine(startX, startY, endX, endY, paint);
        }
    }

    private void drawMiddleStreak(Canvas canvas) {
        int paddingNumber = 8;
        int strokeStreakWidth = 1;
        int currentRadius = radius + paddingNumber;
        int shortStreakWidth = streakWidth * 2 / 3;
        paint.reset();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(strokeStreakWidth);
        double haftAngle = minAngle / 2;
        for (int index = 0; index < amount; index++) {
            double angle = minAngle * (index + deltaNumber) + haftAngle;
            int startX = (int) (width / 2 + Math.cos(angle) * currentRadius);
            int startY = (int) (height / 2 + Math.sin(angle) * currentRadius);
            int endX = (int) (startX + Math.cos(angle) * shortStreakWidth);
            int endY = (int) (startY + Math.sin(angle) * shortStreakWidth);
            canvas.drawLine(startX, startY, endX, endY, paint);
        }
    }

    private void drawHand(Canvas canvas, double number) {
        paint.reset();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(10);
        double angle = (number / distance + deltaNumber) * minAngle;
        int handRadius = radius - handTruncation;
        canvas.drawLine(
                (float) (width / 2 + Math.cos(angle) * centerRadius),
                (float) (height / 2 + Math.sin(angle) * centerRadius),
                (float) (width / 2 + Math.cos(angle) * handRadius),
                (float) (height / 2 + Math.sin(angle) * handRadius),
                paint
        );
        drawRedPoint(canvas, angle);
    }

    private void drawRedPoint(Canvas canvas, double angle) {
        paint.reset();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(7);
        canvas.drawPoint((float) (width / 2 + Math.cos(angle) * radius),
                (float) (height / 2 + Math.sin(angle) * radius), paint);
    }

    private void drawGreenPoint(Canvas canvas, double number) {
        paint.reset();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10);
        int currentRadius = radius/4;
        double angle = (number / distance + deltaNumber) * minAngle;
        canvas.drawPoint((float) (width / 2 + Math.cos(angle) * currentRadius),
                (float) (height / 2 + Math.sin(angle) * currentRadius), paint);
    }
}