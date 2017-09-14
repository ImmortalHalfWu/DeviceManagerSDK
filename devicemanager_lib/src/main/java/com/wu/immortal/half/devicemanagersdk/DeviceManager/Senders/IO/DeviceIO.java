package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO;

import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces.ConnectHostRunnableCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces.StreamCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;

import java.io.IOException;

/**
 * Do :
 * Created : immortalHalfWu
 * Time : 2017/9/13  13:47
 */

@Deprecated
public class DeviceIO {
    private static DeviceIO deviceIO;
    private UsbSerialPort hostSerialPort;
    private Handler threadHandle;
    private UsbManager usbManager;
    private DeviceIO.MyConnectHostRunnableCallBack myCallBack;

    public static DeviceIO install() {
        return deviceIO == null?(deviceIO = new DeviceIO()):deviceIO;
    }

    private DeviceIO() {
    }

    public DeviceIO(UsbManager usbManager) {
        this.usbManager = usbManager;
        HandlerThread handlerThread = new HandlerThread("DeviceIO");
        handlerThread.start();
        this.threadHandle = new Handler(handlerThread.getLooper());
    }

    public void open(UsbSerialPort usbSerialPort, ConnectHostRunnableCallBack connectHostRunnableCallBack) {
        this.threadHandle.post(new ConnectHostRunnable(new DeviceIO.MyConnectHostRunnableCallBack(connectHostRunnableCallBack), this.usbManager, usbSerialPort));
    }

    public void open(UsbManager usbManager, UsbSerialPort usbSerialPort, ConnectHostRunnableCallBack connectHostRunnableCallBack) {
        if(this.myCallBack == null) {
            this.myCallBack = new DeviceIO.MyConnectHostRunnableCallBack(connectHostRunnableCallBack);
        }

        this.threadHandle.post(new ConnectHostRunnable(this.myCallBack, usbManager, usbSerialPort));
    }

    private void sendMsgToDevice(String... msgs) {
        String[] var2 = msgs;
        int var3 = msgs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String msg = var2[var4];
            this.threadHandle.post(new OutputRunnable(this.hostSerialPort, msg, new StreamCallBack() {
                public void operationMsgSucc(@NonNull String msg) {
                }

                public void operationMsgErro(@Nullable String msg, DeviceException e) {
                }

                public boolean isCancle() {
                    return false;
                }
            }));
        }

        this.threadHandle.post(new InputRunnable(this.hostSerialPort, new StreamCallBack() {
            public void operationMsgSucc(@NonNull String msg) {
            }

            public void operationMsgErro(@Nullable String msg, DeviceException e) {
            }

            public boolean isCancle() {
                return false;
            }
        }));
    }

    public void startFinger() {
        this.sendMsgToDevice(new String[]{"5AA50300080101030A03FFFFFF031A", "5AA50300080101020B03FFFFFF031A", "5AA50300080101021003FFFFFF031F"});
    }

    public void startIDCard() {
        this.sendMsgToDevice(new String[]{"5AA50300080101030B03FFFFFF031B", "5AA50300080101020A03FFFFFF0319", "5AA50300080101021003FFFFFF031F"});
    }

    private class MyConnectHostRunnableCallBack implements ConnectHostRunnableCallBack {
        private ConnectHostRunnableCallBack outConnectHostRunnableCallBack;

        public MyConnectHostRunnableCallBack(ConnectHostRunnableCallBack outConnectHostRunnableCallBack) {
            this.outConnectHostRunnableCallBack = outConnectHostRunnableCallBack;
        }

        public void openSuc(UsbSerialPort SerialPort) {
            if(DeviceIO.this.hostSerialPort != null) {
                try {
                    DeviceIO.this.hostSerialPort.close();
                } catch (IOException var11) {
                    var11.printStackTrace();
                } finally {
                    try {
                        DeviceIO.this.hostSerialPort.close();
                    } catch (IOException var10) {
                        var10.printStackTrace();
                    }

                }
            }

            DeviceIO.this.hostSerialPort = SerialPort;
            this.outConnectHostRunnableCallBack.openSuc(SerialPort);
        }

        public void openFail(DeviceException e) {
            this.outConnectHostRunnableCallBack.openFail(e);
        }
    }
}
