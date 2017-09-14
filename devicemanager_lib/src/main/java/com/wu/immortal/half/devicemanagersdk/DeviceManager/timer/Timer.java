package com.wu.immortal.half.devicemanagersdk.DeviceManager.timer;

import android.support.annotation.NonNull;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Loger;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.timer.interfaces.TimeOutCallback;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.timer.interfaces.TimeOutInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * <p>Class : Timer
 * <p>Author : ImmortalHalfWu
 * <p>Time : 2017/6/1 13:28
 * <p>Todo : 指定时间后回调
 * <p>
*/
public class Timer implements TimeOutInterface {

    private static Timer timer;

    private final ScheduledExecutorService executor;
    private final Map<TimeOutCallback,ScheduledFuture<?>> futures;

    public static Timer newInstance() {
        return timer == null ? timer = new Timer() : timer;
    }

    private Timer(){
        executor = Executors
                .newScheduledThreadPool(1);
        futures = new HashMap<>();
    }

    @Override
    public ScheduledFuture<?> schedule(@NonNull TimeOutCallback callback, long time) {
        synchronized (this.futures){
            logi("schedule : 开启延时回调," +callback.hashCode() + "，时间：" + time);
            ScheduledFuture<?> future = executor.schedule(TimerRunbale.newInstance(new MyTimeOutCallback(callback)), time, TimeUnit.MILLISECONDS);
            futures.put(callback,future);
            return future;
        }
    }

    @Override
    public void cancleCallback(@NonNull TimeOutCallback callback) {

        synchronized (this.futures){
            logi("schedule : 取消延时回调," +callback.hashCode());
            ScheduledFuture<?> scheduledFuture = futures.get(callback);
            if (scheduledFuture!=null && !scheduledFuture.isDone()){
                scheduledFuture.cancel(true);
                logi("schedule : 取消延时回调成功 :" +callback.hashCode());
            }else {
                logi("schedule : 取消延时回调失败，运行结束或已取消：" +callback.hashCode());
                return;
            }
            removeCallback(callback);
        }

    }

    private void removeCallback(@NonNull TimeOutCallback callback){
        synchronized (this.futures){
            logi("removeCallback() : 移除队列中的回调对象 : " + callback.hashCode());
            futures.remove(callback);
        }
    }

    private class MyTimeOutCallback implements TimeOutCallback{
        private TimeOutCallback outCallback;
        private MyTimeOutCallback(TimeOutCallback outCallback) {
            this.outCallback = outCallback;
        }
        @Override
        public void timeOut() {
            logi("timeOut() : 延时回调成功 : " + outCallback.hashCode());
            removeCallback(outCallback);
            outCallback.timeOut();
            outCallback = null;
        }
    }

    private static final String TAG = "Timer : ";
    private void logi(String msg){
        Loger.i(TAG + msg);
    }

}
