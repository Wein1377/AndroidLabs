package com.mirea.zarin.mireaproject.practice5;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.FileDescriptor;
import java.io.IOException;

public class AudioService extends Service
{
    private final IBinder binder = new LocalBinder();

    private MediaPlayer mediaPlayer = new MediaPlayer();

    public class LocalBinder extends Binder
    {
        public AudioService getService()
        {
            return AudioService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return binder;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    public void play(FileDescriptor fileDescriptor)
    {
        createMediaPlayer(fileDescriptor);
        mediaPlayer.start();
    }

    public void stop()
    {
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer()
    {
        if (this.mediaPlayer != null)
        {
            this.mediaPlayer.reset();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
    }

    private void createMediaPlayer(FileDescriptor fileDescriptor)
    {
        this.mediaPlayer = new MediaPlayer();
        this.mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

        try
        {
            this.mediaPlayer.setDataSource(fileDescriptor);
            this.mediaPlayer.prepare();
            this.mediaPlayer.setOnCompletionListener(mp ->
            {
                releaseMediaPlayer();
                stopSelf();

                AudioRecorder.startPlayingButton.setEnabled(true);
                AudioRecorder.stopPlayingButton.setEnabled(false);
                AudioRecorder.startRecordingButton.setEnabled(true);
                AudioRecorder.stopRecordingButton.setEnabled(false);
            });
        }
        catch (IOException ignored) { }
    }
}
