package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvTimer;
    private boolean isTimerRunning;
    private long startTime;
    private long seconds;
    private Handler handler;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long elapsedTime = System.currentTimeMillis() - startTime;
            int hours = (int) (elapsedTime / 3600000);
            int minutes = (int) (elapsedTime % 3600000) / 60000;
            int secs = (int) (elapsedTime % 60000) / 1000;
            int msecs = (int) (elapsedTime % 1000);

            String time = String.format("%02d:%02d:%02d:%03d", hours, minutes, secs, msecs);
            tvTimer.setText(time);

            if (isTimerRunning) {
                handler.postDelayed(this, 1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tvTimer);
        handler = new Handler();
    }

    public void startTimer(View view) {
        if (!isTimerRunning) {
            isTimerRunning = true;
            startTime = System.currentTimeMillis() - (seconds * 1000);
            handler.post(runnable);
        }
    }

    public void stopTimer(View view) {
        if (isTimerRunning) {
            isTimerRunning = false;
            handler.removeCallbacks(runnable);
        }
    }

    public void resetTimer(View view) {
        stopTimer(view);
        seconds = 0;
        tvTimer.setText("00:00:00:000");
    }
}
