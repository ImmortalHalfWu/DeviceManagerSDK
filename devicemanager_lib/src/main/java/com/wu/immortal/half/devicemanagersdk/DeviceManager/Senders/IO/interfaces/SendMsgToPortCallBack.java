package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;

/**
 * Do :
 * Created : immortalHalfWu
 * Time : 2017/9/13  13:46
 */

public interface SendMsgToPortCallBack {
    void sendSucc();

    void sendErro(DeviceException var1);
}
