package edu.uw.servicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by sii92_000 on 5/9/2017.
 */

public class CountingService extends IntentService {

    public static final String TAG = "CountingService";
    private int count;
    private Handler mHandler;

    public CountingService() {
        super("CountingService");
    }

    @Override
    public void onCreate() {
        Log.v(TAG, "Creating CountingService");
        mHandler = new Handler();
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.v(TAG, "Received: " + intent.toString());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.v(TAG, "Handling intent");

        for (count = 0; count < 10; count++) {
            Log.v(TAG, "count: " + count);

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CountingService.this, ""+count, Toast.LENGTH_SHORT).show();
                }
            });

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
