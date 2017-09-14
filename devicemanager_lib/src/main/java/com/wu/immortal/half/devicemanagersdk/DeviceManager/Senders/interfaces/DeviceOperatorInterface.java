// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DeviceOperatorInterface.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces;

import android.content.Context;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;

// Referenced classes of package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces:
//			OpenPluginInterface, ClosePluginInterface, CloseAllPluginCallBack

public interface DeviceOperatorInterface
	extends OpenPluginInterface, ClosePluginInterface
{

	public abstract void closeAllPluginDevice(Context context, UsbSerialPort usbserialport, CloseAllPluginCallBack closeallplugincallback);
}
