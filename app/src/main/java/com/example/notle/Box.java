package com.example.notle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Box {
    protected int textColor, bgColor;
    protected String letter;
    protected int x, y, width, height;

    //box constructor
    public Box (int x, int y, String letter) {
        this.x = x;
        this.y = y;
        this.letter = letter;
        textColor = Color.BLACK;
        bgColor = Color.WHITE;
    }

    //draws a box
    public void draw (Canvas canvas) {
        Paint outlinePaint = new Paint();
        outlinePaint.setColor(Color.BLACK);
        outlinePaint.setStyle(Paint.Style.STROKE);

        Paint fillPaint =  new Paint();
        fillPaint.setColor(bgColor);
        fillPaint.setStyle(Paint.Style.FILL);

        Paint textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(100);

        canvas.drawRect(x, y, x+width, y+height, fillPaint);
        canvas.drawRect(x, y, x+width, y+height, outlinePaint);

        if (letter.length() > 1) {
            canvas.drawText(letter, x + Constants.LETTERPADDINGX2, y + Constants.LETTERPADDINGY, textPaint);
        } else {
            canvas.drawText(letter, x + Constants.LETTERPADDINGX, y + Constants.LETTERPADDINGY, textPaint);
        }
    }

    //getters and setters
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter (String letter) {
        this.letter = letter;
    }
}
