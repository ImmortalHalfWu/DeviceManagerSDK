// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CloseAllPluginInterface.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces;

import android.content.Context;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;
import java.util.List;

// Referenced classes of package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces:
//			CloseAllPluginCallBack, ClosePluginInterface

public interface CloseAllPluginInterface
{

	public abstract void CloseAllPluginDevice(Context context, UsbSerialPort usbserialport, CloseAllPluginCallBack closeallplugincallback, List<DeviceEnum> list, ClosePluginInterface closeplugininterface);
}
