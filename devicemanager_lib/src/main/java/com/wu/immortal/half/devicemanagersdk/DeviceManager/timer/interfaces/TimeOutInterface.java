package com.wu.immortal.half.devicemanagersdk.DeviceManager.timer.interfaces;

import android.support.annotation.NonNull;

import java.util.concurrent.ScheduledFuture;

/**
 * Created by ImmortalHalfWu on 2017/6/1 13:08.
 */

public interface TimeOutInterface {

    ScheduledFuture<?> schedule(@NonNull TimeOutCallback callback, long time);
    void cancleCallback(@NonNull TimeOutCallback callback);

}
