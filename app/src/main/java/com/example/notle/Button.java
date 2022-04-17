package com.example.notle;

public class Button {
    protected int x, y, width, height;
    public Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isClicked (float px, float py) {
        return px > x && px < x+width && py > y && py < y +height;
    }
}
