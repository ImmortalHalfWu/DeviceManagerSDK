package com.wu.immortal.half.devicemanagersdk.DeviceManager.threads;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.threads.interfaces.ThreadSchedulerInterface;

/**
 * Do :
 * Created : immortalHalfWu
 * Time : 2017/9/13  14:09
 */

public class ThreadScheduler implements ThreadSchedulerInterface {
    private static ThreadScheduler threadScheduler;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private Handler threadHandler;

    public static ThreadScheduler newInstance() {
        return threadScheduler == null?(threadScheduler = new ThreadScheduler()):threadScheduler;
    }

    private ThreadScheduler() {
        HandlerThread threadHandle = new HandlerThread(this.getClass().getSimpleName());
        threadHandle.start();
        this.threadHandler = new Handler(threadHandle.getLooper());
    }

    public void runningMain(@NonNull Runnable runnable) {
        this.mainHandler.post(runnable);
    }

    public void runningThread(@NonNull Runnable runnable) {
        this.threadHandler.post(runnable);
    }
}
