package com.example.qlsv;
//https://vnso-zn-15-tf-a128-zmp3.zmdcdn.me/442c3abe55059f7d9e8455b68e5e1ba7?authen=exp=1700893454~acl=/442c3abe55059f7d9e8455b68e5e1ba7/*~hmac=8206623406d3fa4bdd427c0692a0249b&fs=MTmUsICwMDmUsICyMDY1NDMxOXx3ZWJWNnwxMDk3ODkzNzgxfDEdUngNTMdUngNTYdUngMjQ5&filename=Ngau-Hung-Hoaprox.mp3
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity5 extends AppCompatActivity {

        Button btnsang;
        private static final String MP3_URL = "https://vnso-zn-15-tf-a128-zmp3.zmdcdn.me/442c3abe55059f7d9e8455b68e5e1ba7?authen=exp=1700893454~acl=/442c3abe55059f7d9e8455b68e5e1ba7/*~hmac=8206623406d3fa4bdd427c0692a0249b&fs=MTmUsICwMDmUsICyMDY1NDMxOXx3ZWJWNnwxMDk3ODkzNzgxfDEdUngNTMdUngNTYdUngMjQ5&filename=Ngau-Hung-Hoaprox.mp3";
        private MediaPlayer mediaPlayer;
        private DownloadTask downloadTask;
        private boolean wasdown=true;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main5);
            btnsang=findViewById(R.id.sanglab);
            btnsang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),MainActivity6.class);
                    startActivity(intent);

                }
            });
            mediaPlayer = new MediaPlayer();
            downloadTask = new DownloadTask(mediaPlayer);

            Button playButton = findViewById(R.id.playButton);
            Button pauseButton = findViewById(R.id.pauseButton);

            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(wasdown){
                    downloadTask.execute(MP3_URL);
                    wasdown=false;
                    }
                    else{
                        mediaPlayer.start();

                    }

                }
            });
            pauseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                }
            });
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            mediaPlayer.release();
            mediaPlayer = null;
        }

}

