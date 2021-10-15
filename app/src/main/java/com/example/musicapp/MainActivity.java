package com.example.musicapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button play, pause, stop;
    ImageView add;
    MediaPlayer mp3Player;
    int pauseCurrentPosition;
    Intent intent;
    Uri path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play_button);
        pause = findViewById(R.id.pause_button);
        stop = findViewById(R.id.stop_button);
        add = findViewById(R.id.image);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/mp3");
                try {
                    startActivityForResult(intent, 10);
                } catch (ActivityNotFoundException e) {

                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_button:
                if (mp3Player == null) {
                    mp3Player = MediaPlayer.create(getApplicationContext(), path);
                    mp3Player.start();
                } else if (!mp3Player.isPlaying()) {
                    mp3Player.seekTo(pauseCurrentPosition);
                    mp3Player.start();
                }
                break;

            case R.id.pause_button:
                if (mp3Player != null) {
                    mp3Player.pause();
                    pauseCurrentPosition = mp3Player.getCurrentPosition();
                }
                break;

            case R.id.stop_button:
                if (mp3Player != null) {
                    mp3Player.stop();
                    mp3Player = null;
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    path = data.getData();
                }
                break;
        }
    }

}