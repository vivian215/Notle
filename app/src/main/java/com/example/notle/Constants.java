package com.example.notle;

import android.graphics.Color;

public interface Constants {
    int BOARDSTARTX = 130;
    int BOARDSTARTY = 300;
    int BOARDSPACEX = 20;
    int BOARDSPACEY = 20;

    int GUESSBOXWIDTH = 150;
    int GUESSBOXHEIGHT = 150;
    int LETTERPADDINGX = GUESSBOXWIDTH*3/11;
    int LETTERPADDINGX2 = GUESSBOXWIDTH/10;
    int LETTERPADDINGY = GUESSBOXHEIGHT*3/4;

    int BUTTONHEIGHT = 200;

    int PIANOHEIGHT = 800;

    int BUTTONPADDINGY = 120;

    int GREEN = Color.parseColor("#32a852");
    int YELLOW = Color.parseColor("#dbcf48");
    int GRAY = Color.GRAY;
    int WHITE = Color.WHITE;
}
