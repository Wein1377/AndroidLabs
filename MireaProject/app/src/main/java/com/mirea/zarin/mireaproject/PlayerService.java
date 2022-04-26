package com.mirea.zarin.mireaproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class PlayerService extends Service
{
    MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate()
    {
        mediaPlayer = MediaPlayer.create(this, MusicFragment.GetSongId());
        mediaPlayer.setLooping(true);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        mediaPlayer.start();
        return START_STICKY;
    }
    @Override
    public void onDestroy()
    {
        mediaPlayer.stop();
    }
}