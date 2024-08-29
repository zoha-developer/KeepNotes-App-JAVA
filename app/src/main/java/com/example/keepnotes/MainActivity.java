package com.example.keepnotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize ProgressBar
        progressBar = findViewById(R.id.progressBar);

        // Show ProgressBar while loading
        progressBar.setVisibility(View.VISIBLE);

        // Use Handler to delay action and hide ProgressBar
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(MainActivity.this, AddNote_AllScreen.class));
                finish();
            }
        }, 2000); // 4 seconds delay
    }
}
