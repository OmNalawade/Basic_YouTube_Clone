package com.example.youtubeclone;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.youtubeclone.Fragment.WatchFragment;

public class WatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);

        int videoRes = getIntent().getIntExtra("video", 0);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.watch_container, WatchFragment.newInstance(videoRes))
                .commit();
    }
}
