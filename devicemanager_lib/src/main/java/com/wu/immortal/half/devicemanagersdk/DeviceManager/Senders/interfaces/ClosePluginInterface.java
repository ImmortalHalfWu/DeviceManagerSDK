// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ClosePluginInterface.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces;

import android.content.Context;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;

// Referenced classes of package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces:
//			OperationPluginCallBack

public interface ClosePluginInterface
{

	public abstract void closePluginDevice(Context context, DeviceEnum deviceenum, UsbSerialPort usbserialport, OperationPluginCallBack operationplugincallback);

	public abstract void free();
}
