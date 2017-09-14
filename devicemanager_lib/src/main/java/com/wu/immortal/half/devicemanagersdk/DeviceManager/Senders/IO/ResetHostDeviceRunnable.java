package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces.OpenHostDeviceCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Loger;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Utils;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.recevices.ADReceiverCallBacker;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.recevices.DeviceADReceiver;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.recevices.interfaces.ADReceiverDispenserInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;

/**
 * Do :
 * Created : immortalHalfWu
 * Time : 2017/9/13  13:49
 */

public class ResetHostDeviceRunnable implements Runnable {
    private Context context;
    private OpenHostDeviceCallBack callBack;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private DeviceADReceiver adReceiver;

    static ResetHostDeviceRunnable newInstans(@NonNull Context context, @NonNull OpenHostDeviceCallBack callBack) {
        return new ResetHostDeviceRunnable(context, callBack);
    }

    static ResetHostDeviceRunnable newInstansAndStart(@NonNull Context context, @NonNull OpenHostDeviceCallBack callBack) {
        return newInstans(context, callBack).start();
    }

    private ResetHostDeviceRunnable(Context context, OpenHostDeviceCallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        this.adReceiver = DeviceADReceiver.newInstans();
    }

    private ResetHostDeviceRunnable start() {
        this.mainHandler.post(this);
        return this;
    }

    public void run() {
        Loger.i("ResetHostDeviceRunnable : run() 注册插拔广播接收器");
        this.adReceiver.registe(this.context, ADReceiverCallBacker.newInstans(new ADReceiverDispenserInterface() {
            public void attachedDevice(Intent intent, DeviceEnum deviceEnum, UsbDevice attachedDevice) {
                if(ResetHostDeviceRunnable.this.callBack != null) {
                    Loger.i("ResetHostDeviceRunnable : attachedDevice 贴合设备为 " + deviceEnum);
                    if(deviceEnum == DeviceEnum.DEVICE_HOST) {
                        try {
                            Loger.i("ResetHostDeviceRunnable : attachedDevice 寻找hostDevice实例并回调");
                            ResetHostDeviceRunnable.this.callBack.resetHostSucc(attachedDevice, (UsbSerialPort) Utils.findHostDevice((UsbManager)ResetHostDeviceRunnable.this.context.getSystemService(Context.USB_SERVICE)).getPorts().get(0));
                        } catch (Exception var8) {
                            Loger.i("ResetHostDeviceRunnable : attachedDevice 寻找hostDevice实例失败");
                            var8.printStackTrace();
                            ResetHostDeviceRunnable.this.callBack.resetHostErro(var8);
                        } finally {
                            Loger.i("ResetHostDeviceRunnable : attachedDevice 释放资源");
                            ResetHostDeviceRunnable.this.adReceiver.unRegiste(ResetHostDeviceRunnable.this.context);
                            ResetHostDeviceRunnable.this.adReceiver = null;
                            ResetHostDeviceRunnable.this.context = null;
                            ResetHostDeviceRunnable.this.callBack = null;
                            ResetHostDeviceRunnable.this.mainHandler = null;
                        }
                    }

                }
            }
        }));
        Loger.i("ResetHostDeviceRunnable : run() 重置Host device");
        Utils.connectManager();
    }
}
