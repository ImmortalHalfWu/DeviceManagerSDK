package com.wu.immortal.half.devicemanagersdk.DeviceManager.threads.interfaces;

import android.support.annotation.NonNull;

/**
 * Do :
 * Created : immortalHalfWu
 * Time : 2017/9/13  14:09
 */

public interface ThreadSchedulerInterface {
    void runningMain(@NonNull Runnable var1);

    void runningThread(@NonNull Runnable var1);
}
