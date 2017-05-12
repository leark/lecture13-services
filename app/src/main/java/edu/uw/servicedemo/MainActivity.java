package edu.uw.servicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, ServiceConnection {

    private static final String TAG = "Main";
    private MusicService.LocalBinder musicService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        player.setOnCompletionListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(MainActivity.this, MusicService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //when "Start" button is pressed
    public void handleStart(View v){
        Log.i(TAG, "Start pressed");

        Intent intent = new Intent(MainActivity.this, CountingService.class);
        startService(intent);

    }

    //when "Stop" button is pressed
    public void handleStop(View v){
        Log.i(TAG, "Stop pressed");

        stopService(new Intent(MainActivity.this, CountingService.class));

    }


    /* Media controls */
    public void playMedia(View v){
        startService(new Intent(MainActivity.this, MusicService.class));
//        player.start();
    }

    public void pauseMedia(View v){
        musicService.pauseMusic();
//        player.pause();
    }

    public void stopMedia(View v){
        stopService(new Intent(MainActivity.this, MusicService.class));
//        player.stop();
    }


    //when "Quit" button is pressed
    public void handleQuit(View v){
        handleStop(null);
//        player.release();
//        player = null;

        finish(); //end the Activity
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, "Activity destroyed");
        super.onDestroy();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.release();
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        musicService = (MusicService.LocalBinder) iBinder;
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}
