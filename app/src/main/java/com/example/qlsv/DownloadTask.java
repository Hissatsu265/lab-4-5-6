package com.example.qlsv;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

public class DownloadTask extends AsyncTask<String, Void, String> {
    private static final String TAG = "DownloadTask";
    private MediaPlayer mediaPlayer;

    public DownloadTask(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    protected String doInBackground(String... urls) {
        String url = urls[0];
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            Log.e(TAG, "Error downloading MP3: " + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        mediaPlayer.start();
    }
}