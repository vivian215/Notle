package com.example.notle;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        //lets player enter the level they selected
        findViewById(R.id.level1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotleActivity.class);
                intent.putExtra("levelNum", 1);
                startActivity(intent);
            }
        });
        findViewById(R.id.level2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotleActivity.class);
                intent.putExtra("levelNum", 2);
                startActivity(intent);
            }
        });
        findViewById(R.id.level3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotleActivity.class);
                intent.putExtra("levelNum", 3);
                startActivity(intent);
            }
        });
    }
}