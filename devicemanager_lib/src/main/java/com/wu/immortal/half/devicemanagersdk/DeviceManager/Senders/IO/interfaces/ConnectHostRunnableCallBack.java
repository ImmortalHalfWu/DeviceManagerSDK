package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;

/**
 * Do :
 * Created : immortalHalfWu
 * Time : 2017/9/13  13:45
 */

public interface ConnectHostRunnableCallBack {
    void openSuc(UsbSerialPort var1);

    void openFail(DeviceException var1);
}
