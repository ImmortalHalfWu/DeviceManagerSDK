package com.wu.immortal.half.devicemanagersdk.DeviceManager.timer;


import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Loger;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.timer.interfaces.TimeOutCallback;

class TimerRunbale implements Runnable{

    private TimeOutCallback callback;

    static TimerRunbale newInstance(TimeOutCallback callback) {
        return new TimerRunbale(callback);
    }

    private TimerRunbale(TimeOutCallback callback) {
        this.callback = callback;
    }


    @Override
    public void run() {
        Loger.i("TimerRunbale : run() 延时回调，运行线程 = " + Thread.currentThread().getName());
        callback.timeOut();
        callback = null;
    }

}
