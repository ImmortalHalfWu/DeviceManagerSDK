package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces;

import android.hardware.usb.UsbDevice;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;

/**
 * Do :
 * Created : immortalHalfWu
 * Time : 2017/9/13  13:45
 */

public interface OpenHostDeviceCallBack {
    void resetHostSucc(UsbDevice var1, UsbSerialPort var2);

    void resetHostErro(Exception var1);
}
