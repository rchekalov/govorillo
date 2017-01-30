package com.govorillo.govorillo.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.govorillo.govorillo.Singleton;

import java.io.IOException;

public class MediaPlayerService extends Service implements MediaPlayer.OnPreparedListener {
    MediaPlayer mediaPlayer = null;
    private String LOG_TAG = "govorillo_debug";

    public MediaPlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        killMediaPlayer();
        String audio_url = Singleton.getInstance().getPlayUrl();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audio_url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        killMediaPlayer();

        mediaPlayer.setOnPreparedListener(this);
        try {
            playAudio(audio_url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return START_NOT_STICKY;
    }

    private void playAudio(String url) throws Exception
    {
        killMediaPlayer();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        killMediaPlayer();
    }

    private void killMediaPlayer() {
        if(mediaPlayer!=null) {
            try {
                mediaPlayer.release();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onPrepared(MediaPlayer player) {
        player.start();
    }
}
