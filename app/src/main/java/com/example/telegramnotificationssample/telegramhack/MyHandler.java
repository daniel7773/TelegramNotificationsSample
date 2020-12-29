package com.example.telegramnotificationssample.telegramhack;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

public class MyHandler extends Handler {
    public MyHandler(Looper looper) {
        super(looper);
    }


    public void postDelayed(Runnable runnable, int delay, Object token) {
        if (delay < 0) {
            delay = 0;
        }
        postAtTime(runnable, token, SystemClock.uptimeMillis() + delay);
    }
}
