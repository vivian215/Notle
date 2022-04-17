package com.example.notle;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.graphics.Canvas;
import android.graphics.Paint;

public class NotleView extends SurfaceView implements Runnable {
    private Board board;
    private Bitmap piano;
    private int screenWidth, screenHeight;
    private NotleActivity activity;
    private FnButton playMelody, enter, delete;
    private PianoKey[] whiteKeys, blackKeys;
    private boolean won, lost;
    private Bitmap blackBg;
    private String[] answer;
    private int levelNum;
    private MediaPlayer melody;
    private Bitmap notleName;
    private Bitmap backarrow;
    private Intent intent;
    private Paint halfPaint, paint;
    private boolean run;
    private Thread thread;

    public NotleView(Context context, Resources res) {
        //initalizes variables
        super(context);
        intent = getActivity().getIntent();
        levelNum = intent.getIntExtra("levelNum", 1);

        //sets answer melody based on level
        answer = new String[]{"D", "E", "F", "G", "A"};
        melody = MediaPlayer.create(context, R.raw.notle1);
        if (levelNum == 3) {
            answer = new String[]{"G#", "D", "F", "C", "E"};
            melody = MediaPlayer.create(context, R.raw.notle3);
        } else if (levelNum == 2) {
            answer = new String[]{"A", "F", "E", "D#", "B"};
            melody = MediaPlayer.create(context, R.raw.notle2);
        }

        won = false;
        lost = false;

        activity = (NotleActivity) getActivity();
        screenWidth = activity.screenWidth;
        screenHeight = activity.screenHeight;

        //sets up board
        GuessBox[][] arrayBoard = new GuessBox[5][5];
        for (int i = 0; i < arrayBoard.length; i++) {
            for (int j = 0; j < arrayBoard[i].length; j++) {
                int boxX = Constants.BOARDSTARTX + Constants.GUESSBOXWIDTH * j + Constants.BOARDSPACEX * j;
                int boxY = Constants.BOARDSTARTY + Constants.GUESSBOXHEIGHT * i + Constants.BOARDSPACEY * i;
                arrayBoard[i][j] = new GuessBox(boxX, boxY, " ");
            }
        }
        board = new Board(arrayBoard);

        //creates the three purple buttons
        int buttonTop = screenHeight - Constants.PIANOHEIGHT - Constants.BUTTONHEIGHT;
        playMelody = new FnButton(0, buttonTop, screenWidth / 3, Constants.BUTTONHEIGHT, "Play Melody!", screenHeight);
        enter = new FnButton(screenWidth / 3, buttonTop, screenWidth / 3, Constants.BUTTONHEIGHT, "Enter", screenHeight);
        delete = new FnButton(screenWidth * 2 / 3, buttonTop, screenWidth / 3, Constants.BUTTONHEIGHT, "Delete", screenHeight);

        //creates the piano keys
        piano = BitmapFactory.decodeResource(getResources(), R.drawable.pianokeys);
        piano = Bitmap.createScaledBitmap(piano, screenWidth, Constants.PIANOHEIGHT, false);
        whiteKeys = new PianoKey[7];
        blackKeys = new PianoKey[5];

        //creates the logo and back arrow on top
        notleName = BitmapFactory.decodeResource(getResources(), R.drawable.notlename);
        notleName = Bitmap.createScaledBitmap(notleName, screenWidth - 250, 250, false);
        backarrow = BitmapFactory.decodeResource(getResources(), R.drawable.backarrow);
        backarrow = Bitmap.createScaledBitmap(backarrow, 100, 100, false);

        //end screen and paints
        blackBg = BitmapFactory.decodeResource(getResources(), R.drawable.black);
        blackBg = Bitmap.createScaledBitmap(blackBg, screenWidth, screenHeight, false);
        halfPaint = new Paint();
        halfPaint.setAlpha(200);
        paint = new Paint();

        run = true;
    }

    public void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = this.getHolder().lockCanvas();
            canvas.drawARGB(255, 255, 255, 255);

            //paints
            Paint fillPaint = new Paint();
            fillPaint.setARGB(255, 126, 86, 175);

            Paint outlinePaint = new Paint();
            outlinePaint.setColor(Color.BLACK);
            outlinePaint.setStyle(Paint.Style.STROKE);
            outlinePaint.setStrokeWidth(5);

            Paint textPaint = new Paint();
            textPaint.setColor(Color.WHITE);
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setTextSize(120);

            //top bar
            canvas.drawBitmap(notleName, 130, 30, paint);
            canvas.drawBitmap(backarrow, 20, 120, paint);

            //draw buttons
            textPaint.setTextSize(50);
            playMelody.draw(canvas, 40, fillPaint, outlinePaint, textPaint);
            enter.draw(canvas, 130, fillPaint, outlinePaint, textPaint);
            delete.draw(canvas, 110, fillPaint, outlinePaint, textPaint);

            //draw guess board
            for (GuessBox[] i : board.getBoard()) {
                for (GuessBox box : i) {
                    box.draw(canvas);
                }
            }

            //draw keyboard
            canvas.drawBitmap(piano, 0, screenHeight - Constants.PIANOHEIGHT, paint);
            int whiteKeyWidth = screenWidth / 7;
            String whiteKeyLetters = "CDEFGAB";
            for (int i = 0; i < whiteKeys.length; i++) {
                whiteKeys[i] = new PianoKey(i * whiteKeyWidth, screenHeight - Constants.PIANOHEIGHT, whiteKeyWidth, Constants.PIANOHEIGHT, String.valueOf(whiteKeyLetters.charAt(i)));
            }
            int blackKeyWidth = (int) (0.75 * whiteKeyWidth);
            String[] blackKeyLetters = {"C#", "D#", " ", "F#", "G#", "A#"};
            boolean skippedTwo = false;
            for (int i = 0; i < blackKeys.length + 1; i++) {
                if (i == 2) {
                    skippedTwo = true;
                    continue;
                }
                if (skippedTwo)
                    blackKeys[i - 1] = new PianoKey((int) (0.1 * screenWidth + blackKeyWidth * i + 0.029 * screenWidth * i), screenHeight - Constants.PIANOHEIGHT, blackKeyWidth, (int) (0.569 * Constants.PIANOHEIGHT), blackKeyLetters[i]);
                else
                    blackKeys[i] = new PianoKey((int) (0.1 * screenWidth + blackKeyWidth * i + 0.029 * screenWidth * i), screenHeight - Constants.PIANOHEIGHT, blackKeyWidth, (int) (0.569 * Constants.PIANOHEIGHT), blackKeyLetters[i]);
            }

            //draw winning screen
            textPaint.setTextSize(100);
            if (won) {
                canvas.drawBitmap(blackBg, 0, 0, halfPaint);
                canvas.drawText("Congratulations!", 180, screenHeight / 2 - 20, textPaint);
            }

            //draws losing screen
            if (board.getCurrGuess() == 5 && !won) {
                lost = true;
                canvas.drawBitmap(blackBg, 0, 0, halfPaint);
                canvas.drawText("You Lose.", screenWidth / 2 - 200, screenHeight / 2 - 20, textPaint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        while (run) {
            draw();
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {
        thread = new Thread(this);
        thread.start();
    }

    public void pause () {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    public boolean onPressEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();

                //clicked anywhere on screen after winning or losing
                if (won || lost) {
                    return true;
                }
                //clicked back button
                if (x < 200 && y < 200) {
                    return true;
                }
                //clicked a piano key
                for (PianoKey key : blackKeys) {
                    if (key.isClicked(x, y)) {
                        board.addKey(key.getLetter());
                       // isClicked = true;
                        return false;
                    }
                }
                for (PianoKey key : whiteKeys) {
                    if (key.isClicked(x, y)) {
                        board.addKey(key.getLetter());
                    }
                }
                //clicked play melody
                if (playMelody.isClicked(x, y)) {
                    melody.start();
                }
                //clicked enter
                if (enter.isClicked(x, y)) {
                    won = board.check(answer);
                    Log.d("won", String.valueOf(won));
                }
                //clicked delete
                if (delete.isClicked(x, y)) {
                    board.delete();
                }
                break;
        }
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean clicked = onPressEvent(event);
        if (clicked) {
            Activity notleActivity = getActivity();
            Intent intent = new Intent(notleActivity, MainActivity.class);
            run = false;
            notleActivity.startActivity(intent);
        }
        return true;
    }
}
