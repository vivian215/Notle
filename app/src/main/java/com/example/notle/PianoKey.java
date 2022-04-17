package com.example.notle;

public class PianoKey extends Button {
    private String letter;

    public PianoKey (int x, int y, int width, int height, String letter) {
        super(x, y, width, height);
        this.letter = letter;
    }

    public String getLetter() {
        return letter;
    }
}
