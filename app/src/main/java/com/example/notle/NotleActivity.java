package com.example.notle;
import android.graphics.Point;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class NotleActivity extends AppCompatActivity {
    private NotleView notleView;
    public TextToSpeech tts;
    public int screenWidth;
    public int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //get height and width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        notleView = new NotleView(this, getResources());
        setContentView(notleView);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        notleView.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        notleView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notleView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
