package com.example.notle;

public class PianoKey extends Button {
    private String letter;

    //pianokey constructor
    public PianoKey (int x, int y, int width, int height, String letter) {
        super(x, y, width, height);
        this.letter = letter;
    }

    //gets letter
    public String getLetter() {
        return letter;
    }
}
