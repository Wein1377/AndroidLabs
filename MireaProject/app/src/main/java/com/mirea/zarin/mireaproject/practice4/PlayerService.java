package com.mirea.zarin.mireaproject.practice4;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;

import com.mirea.zarin.mireaproject.practice4.MusicFragment;
import com.mirea.zarin.mireaproject.practice5.AudioRecorder;

import java.io.FileDescriptor;
import java.io.IOException;

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