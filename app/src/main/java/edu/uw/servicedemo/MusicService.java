package edu.uw.servicedemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by sii92_000 on 5/9/2017.
 */

public class MusicService extends Service {

    private static final String TAG = "MusicService";
    private MediaPlayer player;
    private String songTitle = "The Entertainer";
    private int PENDING_INTENT_ID = 0;
    private int NOTIFICATION_ID = 1;
    private final IBinder myBinder = new LocalBinder();

    public MusicService() {

    }

    @Override
    public void onCreate() {
        Log.v(TAG, "Service started");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.scott_joplin_the_entertainer_1902);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(), PENDING_INTENT_ID,
                new Intent(getApplicationContext(), MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentTitle("Music Player")
                .setContentText("Now playing: " + songTitle)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();

        startForeground(NOTIFICATION_ID, notification);

        player.start();

        return Service.START_NOT_STICKY;
    }


    public void stopMusic() {
        stopForeground(true);
        player.stop();
        player.release();
        player = null;
    }

    @Override
    public void onDestroy() {
        stopMusic();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public class LocalBinder extends Binder {
        public void pauseMusic() {
            player.pause();
        }
    }

}
