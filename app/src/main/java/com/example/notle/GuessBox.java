package com.example.notle;

public class GuessBox extends Box {
    //guessbox constructor
    public GuessBox (int x, int y, String letter) {
        super(x, y, letter);
        width = Constants.GUESSBOXWIDTH;
        height = Constants.GUESSBOXHEIGHT;
    }
}
