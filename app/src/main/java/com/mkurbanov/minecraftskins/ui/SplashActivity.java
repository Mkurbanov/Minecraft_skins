package com.mkurbanov.minecraftskins.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mkurbanov.minecraftskins.R;
import com.slaviboy.progressbar.ProgressBar;

public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBar;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setPercentage(10);
        closeSplash();
    }

    void closeSplash() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                count++;
                progressBar.setPercentage(10 + (count * 30));

                if (progressBar.getPercentage() == 100) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else
                    handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(runnable, 1000);
    }
}