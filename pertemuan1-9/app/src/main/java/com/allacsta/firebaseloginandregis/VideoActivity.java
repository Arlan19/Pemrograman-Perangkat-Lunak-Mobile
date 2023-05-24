package com.allacsta.firebaseloginandregis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        playVideo();
        videoView.start();
    }

    private void playVideo() {
        videoView = findViewById(R.id.videoView);
        Intent intent = getIntent();
        String urlVideo = intent.getStringExtra("url");

        videoView.setVideoURI(Uri.parse(urlVideo));
        MediaController mediaController = new MediaController(VideoActivity.this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        playVideo();
        videoView.start();
    }
}