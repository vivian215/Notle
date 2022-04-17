package com.example.notle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class FnButton extends Button {
    private String text;
    private RectF rect;
    private int buttonTop, buttonBottom;

    //fnbutton constructor
    public FnButton(int x, int y, int width, int height, String text, int screenHeight) {
        super(x, y, width, height);
        buttonTop = screenHeight-Constants.PIANOHEIGHT-Constants.BUTTONHEIGHT;
        buttonBottom = screenHeight-Constants.PIANOHEIGHT;
        rect = new RectF(x, buttonTop, x + width, buttonBottom);
        this.text = text;
    }

    //draws the button
    public void draw(Canvas canvas, int paddingX, Paint fillPaint, Paint outlinePaint, Paint textPaint) {
        canvas.drawRect(rect, fillPaint);
        canvas.drawRect(rect, outlinePaint);
        canvas.drawText(text, x + paddingX, buttonTop + Constants.BUTTONPADDINGY, textPaint);
    }
}
