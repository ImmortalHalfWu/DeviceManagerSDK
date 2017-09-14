package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces.SendMsgToPortCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces.StreamCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;

/**
 * Do :
 * Created : immortalHalfWu
 * Time : 2017/9/13  13:48
 */

public class DeviceIO2 {
    private Handler threadHandle;
    private static volatile DeviceIO2 deviceIO2;

    private DeviceIO2() {
        HandlerThread handlerThread = new HandlerThread("DeviceIO2.ThreadHandle");
        handlerThread.start();
        this.threadHandle = new Handler(handlerThread.getLooper());
    }

    public static DeviceIO2 install() {
        return deviceIO2 == null?(deviceIO2 = new DeviceIO2()):deviceIO2;
    }

    public void sendMsgToPort(UsbSerialPort usbSerialPort, SendMsgToPortCallBack callBack, String... msgs) {
        DeviceIO2.MyStreamCallBack myCallBack = new DeviceIO2.MyStreamCallBack(callBack, msgs);
        String[] var5 = msgs;
        int var6 = msgs.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            String msg = var5[var7];
            this.threadHandle.post(new OutputRunnable(usbSerialPort, msg, myCallBack));
        }

    }

    private static final class MyStreamCallBack implements StreamCallBack {
        private SendMsgToPortCallBack outStreamCallBack;
        private boolean isCancle = false;
        private String[] msgString;

        MyStreamCallBack(SendMsgToPortCallBack outStreamCallBack, String... msgString) {
            this.outStreamCallBack = outStreamCallBack;
            this.msgString = msgString;
        }

        public void operationMsgSucc(@NonNull String msg) {
            if(this.msgString[this.msgString.length - 1].equals(msg)) {
                this.outStreamCallBack.sendSucc();
                this.free();
            }

        }

        public void operationMsgErro(@Nullable String msg, DeviceException e) {
            if(this.msgString[this.msgString.length - 1].equals(msg)) {
                this.outStreamCallBack.sendErro(e);
                this.free();
            }

        }

        private void free() {
            this.isCancle = true;
            this.outStreamCallBack = null;
            this.msgString = null;
        }

        public boolean isCancle() {
            return this.isCancle;
        }
    }
}
