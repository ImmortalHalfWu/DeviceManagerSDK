package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;

/**
 * Do :
 * Created : immortalHalfWu
 * Time : 2017/9/13  13:46
 */

public interface StreamCallBack {
    void operationMsgSucc(@NonNull String var1);

    void operationMsgErro(@Nullable String var1, DeviceException var2);

    boolean isCancle();
}
